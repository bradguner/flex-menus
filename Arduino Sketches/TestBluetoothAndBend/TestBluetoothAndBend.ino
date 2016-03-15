#include <SoftwareSerial.h>
SoftwareSerial BTserial(12, 11); //RX, TX pins

char c = ' ';
void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  BTserial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  int sensor1 = analogRead(A0);
  int sensor2 = analogRead(A1);
  BTserial.write("Start");
  delay(1000);
  /*BTserial.write(sensor1);
  delay(1000);
  BTserial.write(sensor2);
  delay(1000);*/
  

  /*if (Serial.available()) {
    if (sensor1 >= 650 || sensor1 <= 450) {
      c = 'a';
      BTserial.write(c);
    }
    if (sensor2 >= 650 || sensor2 <= 450) {
      c = 'b';
      BTserial.write(c);
    }
  }*/
}
