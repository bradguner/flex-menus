#include <SoftwareSerial.h>
SoftwareSerial BTserial(12, 11); //RX, TX pins

char c = ' ';
boolean ready = false;
int sensor[2] = {0,0};
void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  BTserial.begin(9600);
}

void loop() {
  readSensors();
  if (ready) {
   BTserial.print(sensor[0]);
   BTserial.print(", ");
   BTserial.print(sensor[1]);
   BTserial.println();
   ready = false;
   delay(500);
  }
}

void readSensors() {
  int sensor1 = analogRead(A0);
  int sensor2 = analogRead(A1);
  if (sensor1 >= 610 || sensor1 <= 450 || sensor2 >= 700 || sensor2 <= 280) {
    sensor[0] = sensor1;
    sensor[1] = sensor2;
    ready = true;
  }
  
  
}

