#include <SoftwareSerial.h>
SoftwareSerial BTserial(12, 11); //RX, TX pins

boolean ready = false;
byte sensor[2] = {0,0};
void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  BTserial.begin(9600);
}

void loop() {
  readSensors();
  if (ready) {
   BTserial.write(sensor[0]);
   BTserial.write(sensor[1]);
   BTserial.write("#");
   ready = false;
   sensor[0] = 0;
   sensor[1] = 0;
   delay(1000);
  }
}

void readSensors() {
  int sensor1 = analogRead(A0);
  int sensor2 = analogRead(A1);
  if (sensor1 >= 610) {
    sensor[0] = byte(1);
    ready = true;
  }
  else if (sensor1 <= 450) {
    sensor[0] = byte(2);
    ready = true;
  }
  if (sensor2 >= 700) {
   sensor[1] = byte(1);
   ready = true;
  }
  else if (sensor2 <= 280) {
   sensor[1] = byte(2);
   ready = true;
  }
}

