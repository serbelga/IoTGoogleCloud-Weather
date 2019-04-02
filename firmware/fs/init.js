load('api_config.js');
load('api_dht.js');
load('api_mqtt.js');
load('api_timer.js');

let topic = '/devices/' + Cfg.get('device.id') + '/state';
let dht = DHT.create(4, DHT.DHT22);

Timer.set(50000, true, function() {
  let msg = JSON.stringify({ t: dht.getTemp(), h: dht.getHumidity() });
  let ok = MQTT.pub(topic, msg, 1);
  print(ok, msg);
}, null);