#include <Servo.h>
 
// Declare global variables
Servo servo, serv_1, serv_2, serv_3, serv_4, serv_5;
int flex_1_pin = A0;
int flex_2_pin = A1;
int pause = 1000;
 
 
void setup() {
  Serial.begin(9600);
  pinMode(7,OUTPUT);
 
  servo.attach(7);
  serv_1.attach(2);
  serv_2.attach(3);
  serv_3.attach(4);
  serv_4.attach(5);
  serv_5.attach(6);
 
  servo.write(90);
  serv_1.write(90);
  serv_2.write(90);
  serv_3.write(90);
  serv_4.write(90);
  serv_5.write(90);
 
  delay(2000);
 
  servo.detach();
  serv_1.detach();
  serv_2.detach();
  serv_3.detach();
  serv_4.detach();
  serv_5.detach();
}
 
void loop() {
  //RESET HAND
  servo.attach(7);
  serv_1.attach(2);
  serv_2.attach(3);
  serv_3.attach(4);
  serv_4.attach(5);
  serv_5.attach(6);
  servo.write(90);
  serv_1.write(90);
  serv_2.write(90);
  serv_3.write(90);
  serv_4.write(90);
  serv_5.write(90);
  delay(1000);
  servo.detach();
  serv_1.detach();
  serv_2.detach();
  serv_3.detach();
  serv_4.detach();
  serv_5.detach();
 
  //GAME START
  shake_hand(3);
  delay(1000);
 
  // * * * * * * * * * * * * *
  // Get hand position and
  // write position to serial
  // * * * * * * * * * * * * *
  int playerMove = get_hand_position();
  while(playerMove == 3){
    playerMove = get_hand_position();
  }
 
  int compMove = generateMove();
  switch(compMove){
    case 0:
      do_rock();
      break;
    case 1:
      do_paper();
      break;
    case 2:
      do_scissors();
      break;
  }
 
  calculateGame(compMove,playerMove);
 
  delay(1000);
 
}
 
// * * * * * * * * * * * * * * * * * * * * * * * * * * *
//
// This function makes the mechanical hand shake n times
//
// * * * * * * * * * * * * * * * * * * * * * * * * * * *
void shake_hand(int n){
 
  servo.attach(7);
 
  for(int j = 1; j<=n;j++){
    for(int i = 60; i<=120;i++){
      servo.write(i);
      delay(10);
    }
  }
 
  servo.write(90);
  delay(1000);
  servo.detach();
}
 
// * * * * * * * * * * * * * * * * * * * * * * * * * *
//
// This function reads analog input from flex sensors
// and returns the position of the players hand
// rock -> 0
// paper -> 1
// scissors -> 2
// error -> 3
// * * * * * * * * * * * * * * * * * * * * * * * * * *
int get_hand_position(){
  //Read analog pins
  int flex_3_read = analogRead(flex_1_pin);
  int flex_4_read = analogRead(flex_2_pin);
 
  //Return hand position
  if((flex_3_read < 70) && (flex_4_read < 70)){
    return 0;
  }
  else if((flex_3_read < 80) && (flex_4_read > 140)){
    return 2;
  }
  else if((flex_3_read > 140) && (flex_4_read > 140)){
    return 1;
  }
  else{
    return 3;
  }
}
 
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
//
// This function makes the mechanical hand show rock position
//
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
void do_rock(){
  servo.attach(7);
  serv_1.attach(2);
  serv_2.attach(3);
  serv_3.attach(4);
  serv_4.attach(5);
  serv_5.attach(6);
 
  servo.write(90);
  serv_1.write(180);
  serv_2.write(180);
  serv_3.write(180);
  serv_4.write(180);
  serv_5.write(180);
 
  delay(1000);
 
  servo.detach();
  serv_1.detach();
  serv_2.detach();
  serv_3.detach();
  serv_4.detach();
  serv_5.detach();
 
}
 
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
//
// This function makes the mechanical hand show paper position
//
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
void do_paper(){
 
  servo.attach(7);
  serv_1.attach(2);
  serv_2.attach(3);
  serv_3.attach(4);
  serv_4.attach(5);
  serv_5.attach(6);
 
  servo.write(90);
  serv_1.write(0);
  serv_2.write(0);
  serv_3.write(0);
  serv_4.write(0);
  serv_5.write(0);
 
  delay(1000);
 
  servo.detach();
  serv_1.detach();
  serv_2.detach();
  serv_3.detach();
  serv_4.detach();
  serv_5.detach();
 
}
 
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
//
// This function makes the mechanical hand show scissors position
//
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
void do_scissors(){
  servo.attach(7);
  serv_1.attach(2);
  serv_2.attach(3);
  serv_3.attach(4);
  serv_4.attach(5);
  serv_5.attach(6);
 
  servo.write(90);
  serv_1.write(180);
  serv_2.write(180);
  serv_3.write(0);
  serv_4.write(0);
  serv_5.write(180);
 
  delay(1000);
 
  servo.detach();
  serv_1.detach();
  serv_2.detach();
  serv_3.detach();
  serv_4.detach();
  serv_5.detach();
}
 
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
//
// This function generates and returns a random int from 0 to 2
//
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
int generateMove(){
  int randomval = (int) random(0,3);
  randomSeed(analogRead(0));
  return randomval;
}
 
// * * * * * * * * * * * * * * * * * *
//
// This function determines game case
//
// * * * * * * * * * * * * * * * * * *
void calculateGame(int compMove, int playerMove){
  //Serial.println("Cpu choose" + (String) compMove);
  //Serial.println("Player choose" + (String) playerMove);
 
  if ((playerMove == 0) && (compMove == 0)){
    Serial.print("0");
  }
 
  else if ((playerMove == 0) && (compMove == 1)){
    Serial.print("1");
  }
 
  else if ((playerMove == 0) && (compMove == 2)){
    Serial.print("2");
  }
 
  else if (playerMove == 1 && compMove == 0){
    Serial.print("3");
  }
  else if (playerMove == 1 && compMove == 1){
    Serial.print("4");
  }
  else if (playerMove == 1 && compMove == 2){
    Serial.print("5");
  }
 
  else if (playerMove == 2 && compMove == 0){
    Serial.print("6");
  }
 
  else if (playerMove == 2 && compMove == 1){
    Serial.print("7");
  }
 
  else if(playerMove == 2 && compMove == 2){
     Serial.print("8");
  }
 
  else{
    //ERROR!
    Serial.print("9");
  }
}