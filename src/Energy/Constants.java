package Energy;

public final class Constants {
    public static final Tersoff[] tersoffs = new Tersoff[3];
    static{
        for (int i = 0; i < 3; i++) {
            tersoffs[i] = new Tersoff();
        }
        //ПАРАМЕТРЫ П-ЛА ТЕРСОФФА ДЛЯ SI
        tersoffs[1].A = 1830.8;  //ЭВ
        tersoffs[1].B = 471.18;//ЭВ
        tersoffs[1].LM = 24.799;//1/НМ
        tersoffs[1].MU = 17.322;//1/НМ
        tersoffs[1].BETTA = 1.1E-6;
        tersoffs[1].N = 0.78734;
        tersoffs[1].C = 100390;
        tersoffs[1].D = 16.217;
        tersoffs[1].H = -0.59825;
        tersoffs[1].R = 0.27;  //НМ
        tersoffs[1].S = 0.3;  //НМ
        tersoffs[1].HI = 1.0;
        tersoffs[1].W = 1.0;

        //ПАРАМЕТРЫ П-ЛА ТЕРСОФФА ДЛЯ GE
        tersoffs[0].A = 1769;         //ЭВ
        tersoffs[0].B = 419.23;    //ЭВ
        tersoffs[0].LM = 24.451;    //1/НМ
        tersoffs[0].MU = 17.047;    //1/НМ
        tersoffs[0].BETTA = 9.0166E-7;
        tersoffs[0].N = 0.75627;
        tersoffs[0].C = 106430;
        tersoffs[0].D = 15.652;
        tersoffs[0].H = -0.43884;
        tersoffs[0].R = 0.28;    //НМ
        tersoffs[0].S = 0.31;          //НМ
        tersoffs[0].HI = 1.0;
        tersoffs[0].W = 1;

        //ПАРАМЕТРЫ П-ЛА ТЕРСОФФА ДЛЯ SI-GE
        tersoffs[2].A = Math.sqrt(tersoffs[0].A * tersoffs[1].A);// ЭВ
        tersoffs[2].B = Math.sqrt(tersoffs[0].B * tersoffs[1].B);// ЭВ
        tersoffs[2].LM = (tersoffs[0].LM + tersoffs[1].LM) * 0.5;//1 / НМ
        tersoffs[2].MU = (tersoffs[0].MU + tersoffs[1].MU) * 0.5;//1 / НМ
        tersoffs[2].R = Math.sqrt(tersoffs[0].R * tersoffs[1].R); //НМ
        tersoffs[2].S = Math.sqrt(tersoffs[0].S * tersoffs[1].S); //НМ
        tersoffs[2].HI = 1.00061;
        tersoffs[2].W = 1.00;
    }

}
