#include <SoftwareSerial.h>

const int RX_PIN = 2;
const int TX_PIN = 3;
SoftwareSerial BTserial(2, 3);
char commandChar = ' ';
void setup ()
{
    Serial.begin (9600);
    Serial.println("Enter AT commands:");
    BTserial.begin(9600);
}

void loop () {
    BTserial.println("Bluetooth Test");
    Serial.println("Bluetooth Test");
    delay(1000);
}
