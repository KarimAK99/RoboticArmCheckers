//Kinematics: Task01 FK

/** TODO
 *  Make the arms move slower
 *    -implement intermediate waypoints via movement trajectory
 *    
 *  Better callibrate arm movements
 *  
 *  Implement python serial communication
 */

//Specification of PWM limits (for servo: TGY 50090M Servo):
//Please enter the servo min and max limits for servo 1 and 2 here
//You can find the corresponding limits on a note in your EDMO-box
int SERVOMIN[]  {123, 132, 107}; //{min Servo1, min Servo 2} ->this is the 'minimum' pulse length count (out of 4096) for each servo
int SERVOMAX[]  {532, 340, 545}; //{max Servo1, max Servo 2} ->this is the 'maximum' pulse length count (out of 4096) for each servo

#define NUM_MOTORS 3 // for now we only use two joints simultaneously
#define LEFTEND -90 // Lower limit of servo angular range -> corresponds to SERVOMIN value
#define RIGHTEND 90 // Upper limit of servo angular range -> corresponds to SERVOMAX value

#include <string.h>
#include <stdlib.h>
#include <Servo.h> 
#include <Wire.h>
#include <Adafruit_PWMServoDriver.h>

float incoming[NUM_MOTORS]; //buffer
float pwmvalue[NUM_MOTORS]; //buffer
float calib[NUM_MOTORS]; // used to calibrate servo offsets
byte i = 0;
char record[100];
char recvchar;
byte indx = 0;
Adafruit_PWMServoDriver pwm = Adafruit_PWMServoDriver();

void setup() 
{
  // Electromagnet code.
  pinMode(6,OUTPUT);
  digitalWrite(6,LOW);

  // Robo-arm code.
  Serial.begin(9600);
  pwm.begin();
  pwm.setPWMFreq(60); 
  delay(10);
  zeroCalib();
  // offsets of angular positions to be determined by students for each motor
  setCalib(0,5); // offset for motor 1
  setCalib(1,7); // offset for motor 2
  setCalib(2,-12); // offset for motor 3

  // Electromagnet code.
  delay(1000);
  
  // Robo-arm code.
  while(!Serial);
}
// main function
// receives bytes from Serial communication
// If full data packages is received, values are extracted
// If data package is correct, motor positions are being updated
void loop() 
{
    if (Serial.available())
    {
        recvchar = Serial.read();
        if (recvchar != '\n')
        { 
            record[indx++] = recvchar;
        }
        else if (recvchar == '\n')
        {
          record[indx] = '\0';
          indx = 0;
          Serial.println(record);
          getData(record); // extract motor positions from data package
          writeToMotor(); // write pwm values to motor
          printData(pwmvalue); // for bebugging send pwm values to monitor
        }
                
    }
}

// extract data from data packages
// expected format: VALUE KOMMA VALUE \n
void getData(char record[])
{
    i = 0;
    // Extract value from record.
    // Why the star?
    char *index = strtok(record, ",");
    while(index != NULL)
    {
        // Convert type from string to float
        incoming[i++] = atof(index); 
        // Wtf even is this?
        index = strtok(NULL, ",");
    }
}

// update servo motor positions
void writeToMotor()
{
    if(i == (NUM_MOTORS+1))
    {
        for (byte j = 0 ; j < (NUM_MOTORS) ; j++)
        { 
          incoming[j] += calib[j];
          pwmvalue[j] = map(incoming[j],LEFTEND,RIGHTEND,SERVOMIN[j],SERVOMAX[j]);
          // do not remove this safety function to avoid hardware damages
          pwmvalue[j] = constrain(pwmvalue[j],SERVOMIN[j],SERVOMAX[j]);
          pwm.setPWM(j, 0, pwmvalue[j]); // function by Adafruit library
          delay(1300);  
        }
        if(incoming[NUM_MOTORS] == 0) electromagnetOff();
        else if(incoming[NUM_MOTORS] == 1) electromagnetOn();
    }
    else
    {
        Serial.println("Enter correct number of values separated by commas!");
    }
}

// Robo-arm code.
// Print data
void printData(float data[])
{
    for (byte j = 0 ; j < NUM_MOTORS ; j++)
    {
      Serial.print(data[j]);
      Serial.print('\t');
    }
    Serial.println(); 
}

// Initialize all calibration values to zero
void zeroCalib()
{
    for (byte j = 0 ; j < NUM_MOTORS ; j++)
      calib[j] = 0;
}

// Update calibration value
void setCalib(int motor,int val)
{
    if(motor < NUM_MOTORS)
        calib[motor] = val;
    else
       Serial.println("Enter a valid motor number"); 
}

// Electromagnetic code.
void electromagnetOff()
{
    digitalWrite(6,LOW);
    Serial.println("Set to low. Turning magnetism on...");
    delay(1300);
}

void electromagnetOn()
{
    digitalWrite(6,HIGH);
    Serial.println("Set to high. Turning magnetism off... Please beware of heat");
    delay(1300);
}
