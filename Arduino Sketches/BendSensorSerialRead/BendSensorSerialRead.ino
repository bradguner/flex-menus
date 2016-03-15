#include <SoftwareSerial.h>

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  int sensor1 = analogRead(A0);
  int sensor2 = analogRead(A1);
  Serial.print("Sensor 1: ");
  Serial.print(sensor1);
  Serial.print("    Sensor 2: ");
  Serial.print(sensor2);
  Serial.println();
}
