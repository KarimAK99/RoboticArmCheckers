//Tutorial07 CPG Control Template


//Calibration of Servo Limits (TGY 50090M Servo)
//Please enter our specific servo limits for servo 1,2, and 3 here
//You can find the corresponding limits in your EDMO box
int SERVOMIN[]  {105, 110, 110}; // this is the 'minimum' pulse length count (out of 4096)
int SERVOMAX[]  {580, 560, 580};// this is the 'maximum' pulse length count (out of 4096)

#include <Wire.h>
#include <Adafruit_PWMServoDriver.h>
 
Adafruit_PWMServoDriver pwm = Adafruit_PWMServoDriver();
unsigned long previousMillis = 0;

// ******** <TODO> **********************
// ******** define parameters ********************** 
double frequency = 10; // oscillator frequency
double rateOfFrequency = 1;
double targetFrequency = 50;
unsigned long timeStep = 10; // period used to update CPG state variables and servo motor control (in milliseconds)


double w = 0; // we assume that all oscillators use same coupling weight
double a = 0; // we assume that all oscillators use same adaptation rate for amplitude
double c = 0; // adaptation rate for frequency and offset

const unsigned int NUM_OSCILLATORS = 3; // this number has to match entries in array osc[]
float calib[NUM_OSCILLATORS]; // used to calibrate servo offsets

typedef struct 
{
    double phase;                       // phase of the oscillation
    double amplitude;                   // amplitude of the oscillation 
    double targetAmplitude;             // amplitude to gradually change to
    double offset;                      // offset for the oscillation (in range of servo 0-180)
    double targetOffset;                // added parameter to offset smoothly
    double rateOfPhase;                 // current rate of change of the phase parameter
    double rateOfAmplitude;             // current rate of change of the amplitude parameter
    double rateOfOffset;                // current rate of change of the offset parameter
    double pos;                         // oscillator output = servos angular position
    uint16_t angle_motor;               // mapped motor value
    double phaseBias[NUM_OSCILLATORS];  // controls pairwise coupling phase bias
    double coupling[NUM_OSCILLATORS];   // controls topology of the network
    

} oscillator;

int pwmvalue[NUM_OSCILLATORS];
int LEFTEND = -90;
int RIGHTEND = 90;
float current[3] = {0,30,0};
int amount0 = 2;
int amount1 = 1;
int DEBUG = 2;
// initalisation with offset 90 for all OSCILLATORS (since servos operate in the range -90 to 90)
oscillator osc[NUM_OSCILLATORS] = 
{
    {0,30,30,90,90,0,0,0,0,0,{PI,0,0},{0,1,0}},
    {0,30,30,90,90,0,0,0,0,0,{-PI,0,PI},{1,0,1}},
    {0,30,30,90,90,0,0,0,0,0,{0,-PI,0},{0,1,0}}
};

// strings for reading input
String commandString, valueString, indexString;

// standard setup method
void setup() 
{
    // initialising serial communication
    Serial.begin(9600);
    
    pwm.begin();
  
    pwm.setPWMFreq(60);  // Analog servos run at ~60 Hz update

    delay(10);
    zeroCalib();
// ******** <TODO> **********************
// ******** set calibration values of OSCILLATORS ********************** 
  // offsets of angular positions to be determined by students for each motor
  //setCalib(0,-2); // offset for motor 0
}

void writeToMotorManual(float pos[3])
{
  Serial.println("WRITE");
  Serial.print(pos[0]);
    Serial.print(pos[1]);
  Serial.print(pos[2]);

  for(byte j = 0; j< NUM_OSCILLATORS ; j++)
  {
          pos[j] += calib[j];
          pwmvalue[j] = map(pos[j],LEFTEND,RIGHTEND,SERVOMIN[j],SERVOMAX[j]);
          // do not remove this safety function to avoid hardware damages
          pwmvalue[j] = constrain(pwmvalue[j],SERVOMIN[j],SERVOMAX[j]);
          pwm.setPWM(j, 0, pwmvalue[j]); // function by Adafruit library
  }        
}
void loop() 
{    
     //  ******** <TODO> **********************
     //  ******** implement your code  here **********************
      
       for (int i = 0; i < NUM_OSCILLATORS; i++) 
       {
          //Serial.print(osc[i].pos);
          //Serial.print(" ");
       }
       Serial.println();
       if(DEBUG == 3)
       {
               cpgCalculation();
       }
       else if (DEBUG == 1 or 2)
       {
        CPG();
       }
       

}

void CPG()
{
  Serial.println("method is called ");
  Serial.println(amount0);
  Serial.println(current[0]);
  if(DEBUG == 0)
  {
    current[2] = -87;
      if(amount0 > 0 && current[0] + amount0 < 90)
    {
      current[0] += amount0;
      
    }
    else if(amount0 > 0 && current[0] + amount0 >= 90)
    {
      amount0 = -amount0;
      current[0] += amount0;
      
    }
    else if(amount0 < 0 && current[0] + amount0>-20)
    {
      current[0]+= amount0;
    }
    else if(amount0 < 0 && current[0] + amount0<=-20)
    {
      amount0 = -amount0;
      current[0]+= amount0;
    }
  
    
    if(amount1 > 0 && current[1] + amount1 < 55)
    {
      current[1] += amount1;
      
    }
    else if(amount1 > 0 && current[1] + amount1 >= 55)
    {
      amount1 = -amount1;
      current[1] += amount1;
      
    }
    else if(amount1 < 0 && current[1] + amount1>0)
    {
      current[1]+= amount1;
    }
    else if(amount1 < 0 && current[1] + amount1<=0)
    {
      amount1 = -amount1;
      current[1]+= amount1;
    }
  }
  else if(DEBUG == 1)
  {
    current[0] = -87;
      if(amount0 > 0 && current[2] + amount0 < 90)
    {
      current[2] += amount0;
      
    }
    else if(amount0 > 0 && current[2] + amount0 >= 90)
    {
      amount0 = -amount0;
      current[2] += amount0;
      
    }
    else if(amount0 < 0 && current[2] + amount0>-20)
    {
      current[2]+= amount0;
    }
    else if(amount0 < 0 && current[2] + amount0<=-20)
    {
      amount0 = -amount0;
      current[2]+= amount0;
    }
  
    
    if(amount1 > 0 && current[1] + amount1 < 55)
    {
      current[1] += amount1;
      
    }
    else if(amount1 > 0 && current[1] + amount1 >= 55)
    {
      amount1 = -amount1;
      current[1] += amount1;
      
    }
    else if(amount1 < 0 && current[1] + amount1>0)
    {
      current[1]+= amount1;
    }
    else if(amount1 < 0 && current[1] + amount1<=0)
    {
      amount1 = -amount1;
      current[1]+= amount1;
    }
  }
  

  writeToMotorManual(current);
  readInput();
  
 
}

void cpgCalculation(){
  Serial.println("calculation runs");
  double phase[NUM_OSCILLATORS];
  for(int i = 0; i< NUM_OSCILLATORS; i++){
    phase[i] = osc[i].phase;
  }
  //update the amplitude and offset and then multiply by 0.001 to avoid getting 0 when passing in through the sin function.
  for(int i =0; i < NUM_OSCILLATORS; i++){
   osc[i].amplitude += a* (osc[i].targetAmplitude - osc[i].amplitude)*timeStep*0.001;
   osc[i].offset += c * (osc[i].targetOffset - osc[i].offset)*timeStep* 0.001;
  
  double phaseSum  =0;
  for(int j =0;j < NUM_OSCILLATORS; j++){
    if(i!=j){
      phaseSum += w * osc[i].amplitude * sin(phase[j] - phase[i] - osc[i].phaseBias[j]);
    }
  }
  osc[i].phase+= (2*PI*frequency + phaseSum)*timeStep*0.001;
  //output position 
  //Serial.print(osc[i].phase);
  osc[i].pos = osc[i].amplitude * sin(osc[i].phase)+osc[i].offset;
  //Serial.print("pos ");
  //Serial.print(i );
  //Serial.print(" = ");
  //Serial.print(osc[i].pos);
  //make into array
  Serial.println(DEBUG);
  
    Serial.print("DEBUG");
    float positions[3] = {osc[0].pos, osc[1].pos, osc[2].pos};
    writeToMotorManual(positions);
  
  

  
  }
}
// method for receiving commands over the serial port
void readInput() 
{
    if (Serial.available()) 
    {
        commandString = Serial.readStringUntil('\n');
        if (commandString.startsWith("b")) 
        {
            // change the target amplitude for the specified oscillator
            DEBUG = 1;
        }
        else if (commandString.startsWith("f")) 
        {
            // change the target amplitude for the specified oscillator
            DEBUG = 0;
        }
        else if (commandString.startsWith("s")) 
        {
            // change the target amplitude for the specified oscillator
            DEBUG = 2;
            current[0] = 0;
            current[1] = 0;
            current[2] = 0;
        }
        else if (commandString.startsWith("CPG"))
        {
          DEBUG == 3;
        }
        else if (commandString.startsWith("amp")) 
        {
            // change the target amplitude for the specified oscillator
            indexString = commandString.substring(4, 5);
            valueString = commandString.substring(6, commandString.length());
            osc[(int) indexString.toInt()].targetAmplitude = (int) valueString.toInt();
        } else if (commandString.startsWith("off")) 
        {
            // change the target offset for the specified oscillator
            indexString = commandString.substring(4, 5);
            valueString = commandString.substring(6, commandString.length());
            osc[(int) indexString.toInt()].targetOffset = (int) valueString.toInt();
        } else if (commandString.startsWith("freq")) 
        {
            // change the target frequency for all oscillators
            valueString = commandString.substring(5, commandString.length());
            targetFrequency = (float) valueString.toFloat();
        } else if (commandString.startsWith("phb")) 
        {
            // change the phase bias between the two specified oscillators
            indexString = commandString.substring(4, 5);
            int index1 = (int) indexString.toInt();
            indexString = commandString.substring(6, 7);
            int index2 = (int) indexString.toInt();
            valueString = commandString.substring(8, commandString.length());
            osc[index1].phaseBias[index2] = (float) valueString.toFloat();
            osc[index2].phaseBias[index1] = -osc[index1].phaseBias[index2];
        } else if (commandString.startsWith("weight")) 
        {
            // change the weight for the adaptation of the rate of change of the phase
            valueString = commandString.substring(7, commandString.length());
            w = (float) valueString.toFloat();
        } else if (commandString.startsWith("print")) 
        {
            // print information about the current state of the oscillators
            Serial.print("Frequency: ");
            Serial.println(frequency);
            for (int i = 0; i < NUM_OSCILLATORS; i++) 
            {
                Serial.print(i);
                Serial.print(": ");
                Serial.print("[");
                Serial.print(osc[i].phase);
                Serial.print(", ");
                Serial.print(osc[i].amplitude);
                Serial.print(", ");
                Serial.print(osc[i].targetAmplitude);
                Serial.print(", ");
                Serial.print(osc[i].offset);
                Serial.print(", ");
                Serial.print(osc[i].targetOffset);
                Serial.print(", ");
                Serial.print(osc[i].rateOfPhase);
                Serial.print(", ");
                Serial.print(osc[i].rateOfAmplitude);
                Serial.print(", ");
                Serial.print(osc[i].rateOfOffset);
                Serial.print(", ");
                Serial.print(osc[i].pos);
                Serial.print(", [");
                Serial.print(osc[i].phaseBias[0]);
                Serial.print(", ");
                Serial.print(osc[i].phaseBias[1]);
                Serial.print(", ");
                Serial.print(osc[i].phaseBias[2]);
                Serial.print("]]");
                Serial.println();
            }
        }
    }
}

void zeroCalib()
{
    for (byte j = 0 ; j < NUM_OSCILLATORS ; j++)
      calib[j] = 0;
}

void setCalib(int motor,int val)
{
    if(motor < NUM_OSCILLATORS)
        calib[motor] = val;
    else
       Serial.println("Motor number not defined for calibration."); 
}
