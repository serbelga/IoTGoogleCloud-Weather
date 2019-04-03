# IoT Google Cloud Weather Station

- ```firmware```: Mongoose OS development for ESP8266
- ```functions```: Firebase Cloud Functions 
- ```android_app```: Android companion app

## Hardware system

- DHT22: Temperature and Humidity sensor
- NodeMCU ESP8266
- Breadboard

<img src="./screenshots/sketch.png" width="720">

### Mongoose OS

```gcloud auth application-default login```

```gcloud projects create YOUR_PROJECT_NAME```

```gcloud projects add-iam-policy-binding YOUR_PROJECT_NAME --member=serviceAccount:cloud-iot@system.gserviceaccount.com --role=roles/pubsub.publisher```

```gcloud config set project YOUR_PROJECT_NAME```

```gcloud pubsub topics create iot-topic```

```gcloud pubsub subscriptions create --topic iot-topic iot-subscription```

```gcloud iot registries create iot-registry --region europe-west1 --event-notification-config=topic=iot-topic```

```javascript
let topic = '/devices/' + Cfg.get('device.id') + '/state';
let dht = DHT.create(4, DHT.DHT22);

Timer.set(50000, true, function() {
  let msg = JSON.stringify({ t: dht.getTemp(), h: dht.getHumidity() });
  let ok = MQTT.pub(topic, msg, 1);
  print(ok, msg);
}, null)
```
