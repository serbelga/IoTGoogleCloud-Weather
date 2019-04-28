// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//


const functions = require("firebase-functions"),
  PubSub = require(`@google-cloud/pubsub`),
  admin = require("firebase-admin");

const app = admin.initializeApp();
const firestore = app.firestore();
var num = 0;

firestore.settings({ timestampsInSnapshots: true });

const auth = app.auth();

exports.deviceState = functions.pubsub.topic('iot-topic').onPublish(async (message) => {
    const deviceId = message.attributes.deviceId;
    const deviceRef = firestore.doc(`device-configs/${deviceId}`);
    try {
      await deviceRef.update({ 'state': message.json, 'online': true, 'timestamp' : admin.firestore.Timestamp.now() });
      console.log(`State updated for ${deviceId}`);
    } catch (error) {
      console.error(`${deviceId} not yet registered to a user`, error);
    }
});

exports.deviceStateArray = functions.pubsub.topic('iot-topic').onPublish(async (message) => {
  const deviceId = message.attributes.deviceId;
  console.log(message)
  const deviceRef = firestore.doc(`device-configs/${deviceId}`);
  try {
    var list = [];
    await deviceRef.get().then((doc) => {
      var data = doc.data()
      if ("states" in data)
        list = data.states;
      if (list.length > 19) {
        list = list.slice(1)
      }
      list.push({'state': message.json});
      deviceRef.update({
        states: list
      });
      return null;  
    }).catch((error) => {
        console.log("Error getting document:", error);
    });
  } catch (error) {
    console.error(`${deviceId} not yet registered to a user`, error);
  }
});

exports.deviceOnlineState = functions.pubsub.topic('online-state').onPublish(async (message) => {
  const logEntry = JSON.parse(Buffer.from(message.data, 'base64').toString());
  const deviceId = logEntry.labels.device_id;

  let online;
  switch (logEntry.jsonPayload.eventType) {
    case 'CONNECT':
      online = true;
      break;
    case 'DISCONNECT':
      online = false;
      break;
    default:
      throw new Error(`Invalid event type received from IoT Core: ${logEntry.jsonPayload.eventType}`);
  }

  // Write the online state into firestore
  const deviceRef = firestore.doc(`device-configs/${deviceId}`);
  try {
    await deviceRef.update({ 'online': online });
    console.log(`Connectivity updated for ${deviceId}`);
  } catch (error) {
    console.error(`${deviceId} not yet registered to a user`, error);
  }
});
