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

```mos gcp-iot-setup --gcp-project YOUR_PROJECT_ID --gcp-region europe-west1 --gcp-registry iot-registry```

```mos put fs/init.js

```javascript
let topic = '/devices/' + Cfg.get('device.id') + '/state';
let dht = DHT.create(4, DHT.DHT22);

Timer.set(50000, true, function() {
  let msg = JSON.stringify({ t: dht.getTemp(), h: dht.getHumidity() });
  let ok = MQTT.pub(topic, msg, 1);
  print(ok, msg);
}, null)
```
