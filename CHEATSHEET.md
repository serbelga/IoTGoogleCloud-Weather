# Google Cloud IoT + Mongoose OS + Firebase

### Pub -> get info from device => Monitoring, …

Mongoose OS pub MQTT -> /devices/‘device.id’/state

### Sub -> Modify config of device

Mongoose OS sub MQTT <- /devices/‘device.id’/config

## Firestore

Document device-config/‘device-id’/ 
```json
{
  online: true,
  state: {}
}
```

## Firebase Cloud Functions

### Pub

```javascript
functions.pubsub.topic(‘’).onPublish(async (message) => {
deviceId = message.attributes.deviceId;
})
```

### Sub -> Modify config of device

```javascript
  functions.firestore.document(‘’).onWrite(async (change, context) => {
})
```
