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


exports.function = functions.pubsub.topic('iot-topic').onPublish(async (message) => {
    const deviceId = message.attributes.deviceId;
    console.log(message)
  
    // Write the device state into firestore
    const deviceRef = firestore.doc(`device-configs/${deviceId}`);
    try {
      // Ensure the device is also marked as 'online' when state is updated
      await deviceRef.update({ 'state': message.json, 'online': true, 'timestamp' : admin.firestore.Timestamp.now() });
      console.log(`State updated for ${deviceId}`);
    } catch (error) {
      console.error(`${deviceId} not yet registered to a user`, error);
    }
});

exports.function1 = functions.pubsub.topic('iot-topic').onPublish(async (message) => {
  const deviceId = message.attributes.deviceId;
  console.log(message)

  // Write the device state into firestore
  const deviceRef = firestore.doc(`device-configs/${deviceId}`);
  try {
    // Ensure the device is also marked as 'online' when state is updated
    var aux = 'states.' + num.toString()
    await deviceRef.update(
      { [aux] : {state : message.json}},
    );
    /*
    await deviceRef.update({ 
      'states' : admin.firestore.FieldValue.arrayUnion({ [num] : {state : message.json}})
    });*/
    /*
    await deviceRef.update({ 
      'array1' : admin.firestore.FieldValue.arrayRemove({index: num--})
    });*/
    if (num >= 2) {
      num = 0;
    } else {
      num++;
    }
  } catch (error) {
    console.error(`${deviceId} not yet registered to a user`, error);
  }
});

exports.functionArray = functions.pubsub.topic('iot-topic').onPublish(async (message) => {
  const deviceId = message.attributes.deviceId;
  console.log(message)

  // Write the device state into firestore
  const deviceRef = firestore.doc(`device-configs/${deviceId}`);
  try {
    // Ensure the device is also marked as 'online' when state is updated
    
    
    await deviceRef.update({ 
      'states1' : admin.firestore.FieldValue.arrayUnion({ [num] : {state : message.json}})
    });
    
    await deviceRef.update({ 
      'states1' : admin.firestore.FieldValue.arrayRemove({index: num--})
    });
    if (num >= 2) {
      num = 0;
    } else {
      num++;
    }
  } catch (error) {
    console.error(`${deviceId} not yet registered to a user`, error);
  }
});