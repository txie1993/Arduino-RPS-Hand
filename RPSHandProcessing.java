import processing.serial.*;
String xLabel = "Category";
String yLabel = "Counts";
String Heading = "Win/Lose/Tie Rates";
String URL = "David Hachuel, Tony Xie, Michael Yuan";
int NumOfVertDivisions=5; // dark gray
int NumOfVertSubDivisions=10; // light gray
int NumOfBars= 3;
int ScreenWidth = 800, ScreenHeight=600;
int LeftMargin=100;
int RightMArgin=80;
int TextGap=50;
int GraphYposition=80;
float BarPercent = 0.4;
int value;
int temp;
float yRatio = 0.58;
int BarGap, BarWidth, DivisounsWidth;
int[] bars = new int[NumOfBars];

String[] A = {"Win", "Loss", "Tie"};



int cur_x = 100;
int cur_y = 600;
int increm = 2;
Serial arduinoPort;
PFont font;
int totalGames =0;
int tieGames=0;
int playerWins=0;
int cpuWins=0;
int errors = 0;

String dataReading = "";
String [] dataOutput = {"9"};
String [] gameArray;
String errorProcessing = "";
boolean canStart = false;
int CPUPos = 0; 
int arrayLength = 0;
PImage rock;
PImage paper;
PImage scissors;
boolean canMain = true;
int rectX = 620;
int rectY = 180;
int rectW = 145;
int rectH = 50;
int caseOutcome = 9;
String command = "";

void setup(){
  size(800,600);
  rock = loadImage("Rock.png");
  paper = loadImage("Paper.png");
  scissors = loadImage("Scissors.png");
  DivisounsWidth = (ScreenWidth-LeftMargin-RightMArgin)/(NumOfBars);
  BarWidth = int(BarPercent*float(DivisounsWidth));
  BarGap = DivisounsWidth - BarWidth;
  background(255, 255, 255);
  println(Serial.list());
  fill(0);
  font = loadFont("Museo700-Regular-24.vlw");
  
  String portName = Serial.list()[0];
  arduinoPort = new Serial(this, portName, 9600);
}

void draw(){
  if (canMain){
  mainMenu();
  }
  
  if (canMain == false){
    statMenu();
  }
 
  if (canStart){
    calculateGame();   
  }  
}

void statMenu(){
  stroke(0);
  background(255,255,250);
  textFont(font, 24);
  text("Win , Loss, and Tie Rates", 200, 100);
  textFont(font, 18);
  noStroke();
  fill(200);
  rect(rectX, rectY, rectW, rectH);
  fill(0);
  text("Main Menu", rectX + 30, rectY + 30);
  stroke(0);
  Headings(); // Displays bar width, Bar gap or any variable.
  Axis();
  Labels();
  PrintBars();
}
  

void mainMenu(){
  stroke(0);
  background(255,255,255);
  textFont(font, 24);
  text("Welcome to Rock, Paper, Scissors!", 200, 100);
  textFont(font, 18);
  noStroke();
  fill(200);
  rect(rectX, rectY, rectW, rectH);
  fill(0);
  text("Stat Menu", rectX + 30, rectY + 30);
  text("Player wins: " + str(playerWins), 100, 180);
  text("CPU wins: " + str(cpuWins), 100, 200);
  text("Tie games: " + str(tieGames), 100, 220);
  text("Total games: " + str(totalGames), 100, 240);
  switch (caseOutcome){
    case 0:
      textFont(font, 22);
      text("Player chose: ", 100, 380);
      text("CPU chose: ", 600, 380);
      image(rock, 100, 400, 100, 100);
      image(rock, 600, 400, 100, 100);
      text("Tie Game!", 350, 350);
      break;
    case 1:
      textFont(font, 22);
      text("Player chose: ", 100, 380);
      text("CPU chose: ", 600, 380);
      image(rock, 100, 400, 100, 100);
      image(paper, 600, 400, 100, 100);
      text("CPU Wins!", 350, 350);
      break;
    case 2:
      textFont(font, 22);
      text("Player chose: ", 100, 380);
      text("CPU chose: ", 600, 380);
      image(rock, 100, 400, 100, 100);
      image(scissors, 600, 400, 100, 100);
      text("Player Wins!", 350, 350);
      break;
    case 3:
      textFont(font, 22);
      text("Player chose: ", 100, 380);
      text("CPU chose: ", 600, 380);
      image(paper, 100, 400, 100, 100);
      image(rock, 600, 400, 100, 100);
      text("Player Wins!", 350, 350);
      break;
    case 4:
      textFont(font, 22);
      text("Player chose: ", 100, 380);
      text("CPU chose: ", 600, 380);
      image(paper, 100, 400, 100, 100);
      image(paper, 600, 400, 100, 100);
      text("Tie Game!", 350, 350);
      break;
    case 5:
      textFont(font, 22);
      text("Player chose: ", 100, 380);
      text("CPU chose: ", 600, 380);
      image(paper, 100, 400, 100, 100);
      image(scissors, 600, 400, 100, 100);
      text("CPU Wins!", 350, 350);
      break;
    case 6:
      textFont(font, 22);
      text("Player chose: ", 100, 380);
      text("CPU chose: ", 600, 380);
      image(scissors, 100, 400, 100, 100);
      image(rock, 600, 400, 100, 100);
      text("CPU Wins!", 350, 350);
      break;
    case 7:
      textFont(font, 22);
      text("Player chose: ", 100, 380);
      text("CPU chose: ", 600, 380);
      image(scissors, 100, 400, 100, 100);
      image(paper, 600, 400, 100, 100);
      text("Player Wins!", 350, 350);
      break;
    case 8:
      textFont(font, 22);
      text("Player chose: ", 100, 380);
      text("CPU chose: ", 600, 380);
      image(scissors, 100, 400, 100, 100);
      image(scissors, 600, 400, 100, 100);
      text("Tie Game!", 350, 350);
      break;
    case 9:
      image(paper, 100, 400, 100, 100);
      image(rock, 350, 400, 100, 100);
      image(scissors, 600,400,100,100);
      text("Waiting for input....", 350, 350);
      break;
  } 
}

void animateWinner(PImage winner, PImage loser){
  canMain = false;
  int current_winner = 100;
  int current_loser = 600;
  int increment = 1;
    textFont(font, 24);
    for (int i = 0; i < 125; i++){
    image(paper, current_winner, 400, 100, 100);
    image(rock, current_loser, 400, 100, 100);  
    current_winner += increment;
    current_loser -= increment;
    delay(3000);
    mainMenu();
    } 
    canMain = true;
  
}  

void mousePressed () {
  if(buttonOver()){
     //println("true");
     canMain = !canMain;
     //thank fucking god
     println(canMain);
  } else{
    //println("false");
  }
}



    
void serialEvent(Serial arduinoPort){
  dataReading = arduinoPort.readString();
   if(dataReading!=null){
    dataOutput = append(dataOutput, dataReading);
    canStart = true;
    arrayLength = dataOutput.length;
   
  } 
}

void calculateGame(){
  totalGames++;
  canStart = false;
  print(dataOutput);
  if (dataOutput[arrayLength-1] == "9"){
    arrayLength = 1;
  }
  String workPlease = dataOutput[arrayLength-1];
  
  int gameMode = ConvertIntoNumeric(workPlease);
  switch (gameMode){
    case 0:
      println("tie");
      caseOutcome = 0;
      tieGames++;
      break;
    case 1:
      println("CPU");
      caseOutcome = 1; 
      cpuWins++;
      break;
    case 2:
      println("Player");
      caseOutcome = 2;
      playerWins++;
      break;
    case 3:
      println("Player");
      caseOutcome = 3;
      playerWins++;
      break;
    case 4:
      println("Tie");
      caseOutcome = 4;
      tieGames++;
      break;
    case 5: 
       println("CPU");
       caseOutcome = 5;
       cpuWins++;
       break;
    case 6:
      println("CPU");
      caseOutcome = 6;
      cpuWins++;
      break;
    case 7:
      println("Player");
      caseOutcome = 7;
      playerWins++;
      break;
    case 8:
      println("Tie");
      caseOutcome = 8;
      tieGames++;
      break;
    case 9:
      caseOutcome = 9;
      print("error");
      break;
  }
}

private int ConvertIntoNumeric(String xVal)
{
 try
  { 
     return Integer.parseInt(xVal);
  }
 catch(Exception ex) 
  {
     print("exception");
     errors++;
     return 9; 
  }
}

boolean buttonOver()  {
  if (mouseX >= rectX && mouseX <= rectX+rectW && 
      mouseY >= rectY && mouseY <= rectY+rectH) {
    return true;
  } else {
    return false;
  }
}    

void Headings(){
 fill(0);
 text("Win",50,TextGap);
 text("Loss",250,TextGap);
 text("Tie",450,TextGap);
 text(playerWins,100,TextGap);
 text(cpuWins,300,TextGap);
 text(tieGames,520,TextGap);
}
 
 
void PrintBars(){
int c=0;
int[] record = {playerWins, cpuWins, tieGames};
 for (int i=0;i<NumOfBars;i++){
fill((0xe4+c),(255-bars[i]+c),(0x1a+c));
 stroke(90);
 rect(i*(180) + 110, ScreenHeight-GraphYposition, BarWidth, -(record[i] * 10));
 fill(0x2e,0x2a,0x2a);
  text(A[i], i*(180)+130, ScreenHeight-GraphYposition+20 );
 }
}
 
 
void Axis(){
strokeWeight(1);
 stroke(220);
 for(float x=0;x<=NumOfVertSubDivisions;x++){
int bars=(ScreenHeight-GraphYposition)-int(yRatio*(ScreenHeight)*(x/NumOfVertSubDivisions));
 line(LeftMargin-15,bars,ScreenWidth-RightMArgin-DivisounsWidth+50,bars);
 }
 strokeWeight(1);
 stroke(180);
 for(float x=0;x<=NumOfVertDivisions;x++){
int bars=(ScreenHeight-GraphYposition)-int(yRatio*(ScreenHeight)*(x/NumOfVertDivisions));
 line(LeftMargin-15,bars,ScreenWidth-RightMArgin-DivisounsWidth+50,bars);
 }
 strokeWeight(2);
 stroke(90);
 line(LeftMargin-15, ScreenHeight-GraphYposition+2, ScreenWidth-RightMArgin-DivisounsWidth+50, ScreenHeight-GraphYposition+2);
 line(LeftMargin-15,ScreenHeight-GraphYposition+2,LeftMargin-15,GraphYposition);
 strokeWeight(1);
}
 
 
void Labels(){
 textFont(font,18);
 fill(50);
 rotate(radians(-90));
 text(yLabel,-ScreenHeight/2,LeftMargin-45);
 textFont(font,16);
 for(float x=0;x<=NumOfVertDivisions;x++){
int bars=(ScreenHeight-GraphYposition)-int(yRatio*(ScreenHeight)*(x/NumOfVertDivisions));
 //text(round(x),-bars,LeftMargin-20);
 }
 
 
textFont(font,18);
 rotate(radians(90));
 text(xLabel,300,ScreenHeight-GraphYposition+40);
 textFont(font,24);
 fill(50);
 
 textFont(font);
fill(150);
 
 textFont(font);
}