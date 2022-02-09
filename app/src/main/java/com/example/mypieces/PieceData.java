package com.example.mypieces;

enum KeyType
{
    Major, Minor
}
enum RepType
{
    Current, Retired
}

enum StateType
{
    Memorization, Development, Finishing
}

enum GradingType
{
    RCM, ABRSM, AMEB, Trinity
}

enum CompositionType
{
    Anh, Op, BWV
}

public class PieceData {

    String pieceName;
    String composer;
    String upperTimeSig;
    String lowerTimeSig;
    String idealTempo;
    String currentTempo;
    String grade;
    GradingType gradeType;
    String key;
    KeyType keyType;
    String genre;
    String mood;
    String form;
    String why;
    String tempoText;
    String startMonth;
    String collection;
    RepType listType;
    StateType stateType;

}
