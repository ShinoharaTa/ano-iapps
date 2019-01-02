//////////////////////////////////////////////////////////////////////////////////
// ���̊y��i�A�v���� �\�[�X�v���O����
// Created by T.Shinohara  Debuged by toshiT
//////////////////////////////////////////////////////////////////////////////////

/* �e��C���N���[�h */
import com.docomostar.StarApplication;
import com.docomostar.ui.*;
import com.docomostar.opt.ui.*;//�^�b�`�p�l���ŕK�v�ł��B
import com.docomostar.media.*;
import com.docomostar.io.*;
import com.docomostar.system.*;
import com.docomostar.device.*;
import com.docomostar.fs.*;
import com.docomostar.*;
import java.util.*;
import java.io.*;
import javax.microedition.io.*;

/* ���C���̕\���֐��������� */
class MainCanvas extends Canvas implements Runnable{

    /* �O���[�o���ϐ��̐ݒ� */
    String filename = "title.txt";
    StorageDevice sd = null;
    StarAccessToken sat = null;
    byte[] data;
    
    // ���ʍ��W��
    int gX; // �G�������ʒu(x)
    int gY; // �G�����c�ʒu(y)
    int xpos;
    int xpos2;
    int xpostotal;
    int cnt;
    int cact;
    
    // �e��ݒ�Ɏg�p
    int charaSpec;
    int showKeyboard;
    int mbshow;
    int inst;
    int newdata;
    int keyType;
    int ckflg;
    int pattemp;
	int playpt;
    
    // �e��t���O
    int tchflg; // �^�b�`�A�Ŗh�~�t���O
    int keyflg; // �L�[�A�Ŗh�~�t���O
    int moflg;
    int getMenuFlag;
    int showKeyFlag;
    int instFlag;
    int checkflg;
    int playflg;
    
    // ���p�`�h��Ԃ��p
    int triX[] = new int[3]; // �O�p�`X�_���W
    int triY[] = new int[3]; // �O�p�`Y�_���W
    int boxX[] = new int[4]; // �l�p�`X�_���W
    int boxY[] = new int[4]; // �l�p�`Y�_���W
    int starX[] = new int[10]; // ���^X�_���W
    int starY[] = new int[10]; // ���^Y�_���W
    int polix[] = new int[10];
    int poliy[] = new int[10];
    String ck;
    String ck2;
    
    // �c���p������
    int tempX[] = new int[10];
    int tempY[] = new int[10];
    int tempR[] = new int[10];
    int tempRad[] = new int[10];
    int tempPat[] = new int[10];
    
    // ���������p
    int randX; // �L�[���쎞��X�ʒu
    int randY; // �L�[���쎞��Y�ʒu
    int randR; // �}�`�̔��a
    int randRad; // �}�`�̊p�x(Deg�o��)
    int randPat; // �}�`�̕\���p�^�[��
    
    // �V�X�e����Ԏ擾�p
    int sysmail;
    int sysant;
    int sysbatt;
    int sysman;
    
    //int scrdat; // �X�N���[����
    int getKey;
    int i;
    int act;
    int timerCnt; // ��p�^�C�}�[
    int kurow;
    int Playtemp;
    int keyup;
    int vol;
    boolean tch;// �^�b�`�����ۂ��������t���O
    boolean tch2;
    int pattern = 0;
    int setpat;
    long cnt1;
    int COLOR;
    int KURO;
    int KURO2;
    int at;
    String evStus;
    
    Graphics g = getGraphics();
    static final Font font = Font.getDefaultFont();
    Image mikubg = null;
    Image rukabg = null;
    Image lbutton = null;
    Image sbutton = null;
    Image actsbt = null;
    Image actlbt = null;
    Image cbt = null;
    Image cbtact = null;
    Image bar = null;
    Image menubar = null;
    Image bg = null;
    Image modo = null;
    Image subbutton = null;
    AudioPresenter[] ap = new AudioPresenter[4];
    int apIndex = 0;
    MediaSound ms = null;
    
    static final Random random = new Random();
    
    /* �}�N����` */
    static final int MIKU = 0;
    static final int RUKA = 1;
    static final int BTBIG = 0;
    static final int BTSMA = 1;
    static final int BTACTB = 2;
    static final int BTACTS = 3;
    
    /* �F�̐ݒ�  */
    static final int ORANGE = Graphics.getColorOfRGB(230,200,0);
    static final int WHITE = Graphics.getColorOfRGB(255, 255, 255);
    static final int BLACK = Graphics.getColorOfRGB(0, 0, 0);
    static final int CMIKU = Graphics.getColorOfRGB(134, 206, 203);
    static final int CRUKA = Graphics.getColorOfRGB(235, 211, 207);
    static final int LGRAY = Graphics.getColorOfRGB(200, 200, 200);
    static final int BGRAY = Graphics.getColorOfRGB(64, 64, 64);
    static final int GRAY = Graphics.getColorOfRGB(96, 96, 96);
    
    /* ��ʕ\���Ɋւ���ݒ� */
    static final int DISP_WIDTH = Display.getWidth();
    static final int DISP_HEIGHT = Display.getHeight();
    static final int FONT_BASE = font.getHeight();
    static final int KUROS = 14;
    static final int KUROS2 = 28;
    static final int KUROD = 18;
    static final int KUROD2 = 36;
    static final int KUROW = 22;
    static final int KUROW2 = 44;
    static final int KUROWW = 30;
    static final int KUROWW2 = 62;
    
    static final byte[] MIDI_DATA = {
        // MThd�`�����N
        (byte)0x4D, (byte)0x54, (byte)0x68, (byte)0x64, // �}�W�b�N�i���o�[
        (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x06, // �`�����N��
        (byte)0x00, (byte)0x00,                         // �t�H�[�}�b�g
        (byte)0x00, (byte)0x01,                         // �g���b�N��
        (byte)0x00, (byte)0x30,                         // ���ԕ���\ (�l�������̒���)
        // MTrk�`�����N
        (byte)0x4D, (byte)0x54, (byte)0x72, (byte)0x6B, // �}�W�b�N�i���o�[
        (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x12, // �`�����N��
        (byte)0x00, (byte)0xB0, (byte)0x00, (byte)0x79, // �o���N�Z���N�g
                                                        // MIDI_DATA[25] �o���N (0x79: General MIDI)
        (byte)0x00, (byte)0xC0, (byte)0x00,             // �v���O�����`�F���W (http://www.pluto.dti.ne.jp/~daiki/Midi/IL_ProgramNum.html)
                                                        // MIDI_DATA[28] �v���O���� (0x00: �A�R�[�X�e�B�b�N�s�A�m)
        (byte)0x00, (byte)0x90, (byte)0x3C, (byte)0x7F, // �m�[�g�I�� (http://www.pluto.dti.ne.jp/~daiki/Midi/AboutMidi_Note.html)
                                                        // MIDI_DATA[31] ���K (0x3C: �h)
        (byte)0x30, (byte)0x3C, (byte)0x00,             // �m�[�g�I�t
                                                        // MIDI_DATA[33] ���� (0x30: �l��)
        (byte)0x00, (byte)0xFF, (byte)0x2F, (byte)0x00, // �g���b�N�G���h
    };
    static final String instret[] = {
        "Grand Piano","Bright Piano","E.Grand Piano","H-t Piano",
        "E.Piano1","E.Piano2","Harpsichord","Clavi","Celesta",
        "Glockenspiel","Music Box","Vibraphone","Marinmba",
        "Xylophone","Tubular Bells","Dulcimer","Drawbar Organ",
        "Percus Organ","Rock Organ","Church Organ","Reed Organ",
        "Accordion","Harmonica","Tango Acc.","Nylon Guitar",
        "Steel Guitar","Jazz Guitar","Clean Guitar","Muted Guitar",
        "Overdrive Gt.","Distortion Gt.","Gt.harmonics","Acoustic Bass",
        "Finger Bass","Picked Bass","Fretless Bass","Slap Bass1",
        "Slap Bass2","Synth Bass1","Synth Bass2","Violin","Viola",
        "Cello","Contrabass","Tre. Strings","Piz. Strings","Orche. Harp",
        "Timpani","String1","String2","Syn.String1","Syn.String2",
        "Choir Aahs","Voice Oohs","Synth Vox","Orchestra Hit","Trumpet",
        "Trombone","Tuba","Mt.Trumpet","French Horn","Brass",
        "Synth Brass1","Synth Brass2","Soprano Sax","Alto Sax",
        "Tenor Sax","Baritone Sax","Oboe","English Horn","Bossoon",
        "Clarinet","Piccolo","Flute","Recorder","Pan Flute",
        "Blown Bottle","Shakuhachi","Whistle","Ocarina","Square Lead",
        "Saw Lead","Calliope Lead","Chiff Lead","Charang Lead",
        "Voice Lead","5th Lead","Base + Lead","Age Pad","Warm Pad2",
        "Polysynth Pad","Choir Pad","Bowed Pad","Metallic Pad",
        "Halo Pad","Sweep Pad","Rain","Soundtrack","Crystal",
        "Atmosphere","Brightness","Goblin","Echos","SciFx","Sitar",
        "Banjo","Shamisen","Koto","Kalimba","Bag pipe","Fiddle",
        "Shanai","Tinkle Bell","Agogo","Steel Drums","Woodblock",
        "Taiko","Melodic Tom","Synth Drum","Reverse Cym.","Gt.Fret Noise",
        "Breath Noise","Seashore","Bird Tweet","Telephone","Helicopter",
        "Applause","Gunshot",""
    };
    
    int atplay[] = {66,62,57,62,64,69,64,66,64,57,62};
    String databuf[];
    int playbuf[] = new int[200];
    
    MainCanvas() {
        try {
            MediaImage mi = MediaManager.getImage("resource:///mikubg.jpg");
            mi.use();
            mikubg = mi.getImage();
            mi = MediaManager.getImage("resource:///rukabg.jpg");
            mi.use();
            rukabg = mi.getImage();
            mi = MediaManager.getImage("resource:///bg.jpg");
            mi.use();
            bg = mi.getImage();
            mi = MediaManager.getImage("resource:///lbutton.jpg");
            mi.use();
            lbutton = mi.getImage();
            mi = MediaManager.getImage("resource:///sbutton.jpg");
            mi.use();
            sbutton = mi.getImage();
            mi = MediaManager.getImage("resource:///actlbt.jpg");
            mi.use();
            actlbt = mi.getImage();
            mi = MediaManager.getImage("resource:///actsbt.jpg");
            mi.use();
            actsbt = mi.getImage();
            mi = MediaManager.getImage("resource:///bar.jpg");
            mi.use();
            bar = mi.getImage();
            mi = MediaManager.getImage("resource:///cbt.jpg");
            mi.use();
            cbt = mi.getImage();
            mi = MediaManager.getImage("resource:///cbtact.jpg");
            mi.use();
            cbtact = mi.getImage();
            mi = MediaManager.getImage("resource:///mainbar.jpg");
            mi.use();
            menubar = mi.getImage();
            mi = MediaManager.getImage("resource:///modo.jpg");
            mi.use();
            modo = mi.getImage();
            mi = MediaManager.getImage("resource:///subbutton.gif");
            mi.use();
            subbutton = mi.getImage();
            for ( int i = 0; i < ap.length; i++ ) {
                ap[i] = AudioPresenter.getAudioPresenter(i);
            }
            TouchDevice.setEnabled(true);
        } catch (Exception e) {
        }
        pattern = 0;
        showKeyFlag = 0;
        setBackground(BLACK);
        tch = false;
        loadnewdata();
        if( newdata != 84 ){
            newdata = 84;
            inst = 0;
            charaSpec = MIKU;
            showKeyboard = 0;
            kurow = 0;
            keyType = 0;
            savenewdata();
            saveSetting();
        } else {
            loadSetting();
        }
        
        tch=false;//�^�b�`����Ă��Ȃ�
    }
    
    public void paint(Graphics g) {}
    
    //��������̎w��
    public void run() {
        long t;
        int i;
        t = System.currentTimeMillis();
        while(true) {
            draw();
            cnt1++;
            do {
                // �ʃX���b�h�����荞�߂�悤��yield
                Thread.yield();
                if( tch ){
                    gY = TouchDevice.getY();
                    gX = TouchDevice.getX();
                    if( checkflg == 0 ){
                        xpos2 = xpos;
                        xpos = gY;
                        xpostotal = xpos2 - xpos + xpostotal;
                        if( xpostotal < 0 ) xpostotal = 0;
                        if( xpostotal > 130 + 128 * 60 - 784) xpostotal = 7810 - 784;
                    }
                    if( keyType == 1 ){
                        i = keyPos(gX,gY,showKeyboard);
                    } else {
                        i = keyPos2(gX,gY,showKeyboard);
                    }
                    if( i != Playtemp ){
                        Playtemp = i;
                        if( pattern == 31 ){
                            playSound( i );
                        }
                    }
                }
            } while( (System.currentTimeMillis() - t) < 25 );
            t = System.currentTimeMillis();
        }
    }

    public void draw(){
        int j;
        g.lock();
	    g.clearRect( 0, 0, DISP_WIDTH, DISP_HEIGHT );
	    switch( pattern ){
        case 0:
            cnt1 = 0;
            pattern = 1;
            break;

        case 1:
            if( cnt1 > 20 ){
                pattern = 5;
                break;
            }
            i = (int)cnt1 * 20;
            g.setColor(WHITE);
            g.fillRect(33,480,414,35);
            g.setColor(BLACK);
            g.fillRect(37,484,406,27);
            if( charaSpec == MIKU ){
                g.setColor(CMIKU);
            } else {
                g.setColor(CRUKA);
            }
            g.drawString("�N����",240 - font.getBBoxWidth("�N����") / 2,635);
            g.fillRect(40,487,i,21);
            break;

        case 5://�ݒ�̓ǂݍ���
            if( kurow == 0 ){
                KURO = KUROD;
                KURO2 = KUROD2;
            } else if( kurow == 1 ){
                KURO = KUROS;
                KURO2 = KUROS2;
            } else if( kurow == 2 ){
                KURO = KUROW;
                KURO2 = KUROW2;
            } else if( kurow == 3 ){
                KURO = KUROWW;
                KURO2 = KUROWW2;
            }
            pattern = 11;
            i = 0;
            tchflg = 1;
            keyflg = 1;
            break;
            
        case 11:
            if( charaSpec == MIKU ){
                g.drawImage(mikubg,0,0);
                COLOR = CMIKU;
            } else {
                g.drawImage(rukabg,0,0);
                COLOR = CRUKA;
            }
            if( tchflg == 0 ){
                if( gX >= 12 && gX < 468 && gY >= 544 && gY < 602 ){
                    i = 0;
                    menuselect( i );
                }
                if( gX >= 12 && gX < 468 && gY >= 644 && gY < 702 ){
                    i = 1;
                    menuselect( i );
                }
                if( gX >= 12 && gX < 234 && gY >= 714 && gY < 802 ){
                    i = 2;
                    menuselect( i );
                }
                if( gX >= 246 && gX < 468 && gY >= 714 && gY < 802 ){
                    i = 3;
                    menuselect( i );
                }
            }
            if( keyflg == 0 ){
                if( getKey == 21 || getKey == 24 ){
                    i--;
                    keyflg = 1;
                }
                if( getKey == 22 || getKey == 23 ){
                    i++;
                    keyflg = 1;
                }
            }
            if( i < 0 ) i = 0;
            if( i >= 4 ) i = 3;
            if( keyflg == 0 && getKey == 25 ){
                menuselect( i );
            }
            mainmenu( i );
            tchflg = 0;
            stbar();
            break;

        case 31:// �^�b�`�L�[�{�[�h
            if( tchflg == 0 ){
                if( gX >= 396 && gX < 476 && gY >= 4 && gY < 44 ){
                pattern = 41;
                gX = gY = 0;
                tchflg = 1;
                }
			}
            if( keyflg == 0 && keyType == 1 ){
                if( getKey == 33 || getKey == 34 ){
                    if( keyup == 0 ){
                        keyup = 1;
                    } else {
                        keyup = 0;
                    }
                }
                keyflg = 0;
            }
            for(i=9;i>=1;i--){// �c������
                g.setColor(colorset(i));
  	            switch(tempPat[i]){
                case 1: pntLine( tempX[i], tempY[i], tempRad[i] ); break;
                case 2: pntCircle( tempX[i], tempY[i], tempR[i] ); break;
                case 3: pntTri( tempX[i], tempY[i], tempR[i], tempRad[i] ); break;
                case 4: pntBox( tempX[i], tempY[i], tempR[i], tempRad[i] ); break;
                case 5: pntStar( tempX[i], tempY[i], tempR[i], tempRad[i] ); break;
                default: break;
                }
            }
            for(i=9;i>0;i--){
                tempX[i] = tempX[i-1];
                tempY[i] = tempY[i-1];
                tempR[i] = tempR[i-1]+5;
                tempRad[i] = tempRad[i-1];
                tempPat[i] = tempPat[i-1];
            }

            randInit();// ��������
            if( getMenuFlag == 0 ){
                g.setColor(COLOR);
                
                // �L�[����̉��̍Đ�
                if( keyflg == 0 ){
                    if( getKey >= 0 && getKey < 12 ){
                        i = getKey + 60;
                        gX = randX + (getKey%3) * 160;
                        gY = randY + (getKey/3) * 214;
                        if( keyup == 1 ){
                            i = i + 12;
                        }
                        playSound( i );
                    }
                }
                
                // �^�b�`���̉��̍Đ�
                if( tchflg == 1 ){
                    if( keyType == 1 ){
                        i=keyPos( gX, gY, showKeyFlag );
                    } else {
                        i=keyPos2( gX, gY, showKeyFlag );
                    }
                    playSound( i );
                }
                
                // �^�b�`���̐}�`�̕`��
                if( tchflg == 1 || tch ){
                    switch(randPat){
                    case 1: pntLine( gX , gY , randRad ); break;
                    case 2: pntCircle( gX , gY , randR ); break;
                    case 3: pntTri( gX , gY , randR, randRad ); break;
                    case 4: pntBox( gX , gY , randR, randRad ); break;
                    case 5: pntStar( gX , gY , randR, randRad ); break;
                    default: break;
                    }
                    tempX[0] = gX;
                    tempY[0] = gY;
                    tempR[0] = randR;
                    tempRad[0] = randRad;
                    tempPat[0] = randPat;
                } else if( keyflg == 0 ){
                    if( getKey >= 0 && getKey < 12 ){
                        switch(randPat){
                        case 1: pntLine( gX , gY , randRad ); break;
                        case 2: pntCircle( gX , gY , randR ); break;
                        case 3: pntTri( gX , gY , randR, randRad ); break;
                        case 4: pntBox( gX , gY , randR, randRad ); break;
                        case 5: pntStar( gX , gY , randR, randRad ); break;
                        default: break;
                        }
                        tempX[0] = gX;
                        tempY[0] = gY;
                        tempR[0] = randR;
                        tempRad[0] = randRad;
                        tempPat[0] = randPat;
                    }
                } else {
                    tempPat[0] = 0;
                }
            }
            getMenuFlag = 0;
            if( showKeyboard == 0 ){
                if( keyType == 1 ){
                    touchKeyborad();
                } else {
                    touchKeyborad2();
                }
            }
            if( mbshow != 1 ){
	           	g.drawImage(subbutton,396,4);
	           	g.setColor(COLOR);
	            g.drawString("MENU",436 - font.getBBoxWidth("MENU") / 2,35);
	        }
            if( keyflg == 0 && getKey == 32 ){
                pattern = 41;
                gX = gY = 0;
                keyflg = 1;
            }
            tchflg = 0;
            keyflg = 1;
            break;

        case 41:
            act = 0;
            tchflg = 1;
            pattern = 51;
            keyflg = 1;
            setpat = 0;
            break;

        case 51:
            if( tchflg == 1 ){
                if( gX >= 12 && gX < 234 && gY >= 238 && gY < 310 ){
                    act = 0;
                    subselect( act );
                }
                if( gX >= 246 && gX < 369 && gY >= 238 && gY < 310 ){
                    act = 1;
                    subselect( act );
                }
                if( gX >= 12 && gX < 369 && gY >= 340 && gY < 412 ){
                    act = 2;
                    subselect( act );
                }
                if( gX >= 12 && gX < 369 && gY >= 442 && gY < 514 ){
                    act = 3;
                    subselect( act );
                }
                if( gX >= 12 && gX < 369 && gY >= 544 && gY < 616 ){
                    act = 4;
                    subselect( act );
                }

				//�������特�ʊ֌W
                if( gX >= 96 && gX < 361 && gY >= 660 && gY < 692 ){
                	vol = (gX-96)/32;
                }
                if( gX >= 20 && gX < 69 && gY >= 650 && gY < 699 ){
                	vol--;
                }
                if( gX >= 411 && gX < 460 && gY >= 650 && gY < 699 ){
                	vol++;
                }

            }
            if( keyflg != 1 ){
                if( getKey == 21 ){
                    act--;
                    keyflg = 1;
                }
                if( getKey == 23 ){
                    act++;
                    keyflg = 1;
                }
                if( getKey == 22 ){
                	vol--;
                }
                if( getKey == 24 ){
                	vol++;
                }
            }
            if( act < 0 ) act = 0;
            if( act >= 5 ) act = 4;
            if( vol < 0 ) vol = 0;
            if( vol >=9 ) vol = 8;
            
            if( keyflg == 0 && getKey == 25 ){
                subselect( act );
            }
			
			//���ʊ֌W
			g.setColor(WHITE);
            g.fillRect(20,650,49,49);
            g.fillRect(411,650,49,49);
            g.setColor(BGRAY);
            g.fillRect(24,654,41,41);
            g.fillRect(415,654,41,41);
            g.fillRect(112,673,256,5);
            g.setColor(CRUKA);
            g.fillRect(112,673,(vol*32),5);
            g.setColor(CMIKU);
            g.fillArc(104+(vol*32),665,21,21,0,360);
            
                        
            submenu( act );
            tchflg = 0;
            stbar();
            menubar( "�T�u���j���[" );
            tchflg = 0;
            keyflg = 1;
            break;
            
        case 54:
        	pattern = pattemp;
        	saveSetting();
        	break;
            
        case 61:
            act = 0;
            i = 0;
            cnt1 = 0;
            pattern = 71;
            setpat = 0;
            keyflg =1;
            break;
            
            
        case 71:
            g.drawImage(bg,0,0);

            switch( setpat ){
            case 0:
                if( moflg == 0 ){
                    if( tchflg == 0 ){
                        if( gX >= 20 && gX < 440 && gY >= 130 && gY < 130+(60*6) ){
                            setpat = (gY - 130) / 60 + 1;
                            act = getact( setpat-1 );
                            gX = gY = 0;
                            xpostotal = 0;
                            break;
                        }
                    }
                    if( keyflg != 1 && getKey == 25 ){
                        setpat = act + 1;
                        act = getact( setpat-1 );
                        getKey = 0;
                        xpostotal = 0;
                        break;
                    }
                    setting( 6 );
                }
                setpnt("�L�����ݒ�",0,0,1,act);
                setpnt("�L�[�{�[�h�̕\��",1,0,1,act);
                setpnt("MENU�{�^���̕\��",2,0,1,act);
                setpnt("�L�[�̐�",3,0,1,act);
                setpnt("�����Ղ̕�",4,0,1,act);
                setpnt("�����̐ݒ�",5,0,3,act);
                break;
                
            case 1:
                if( moflg == 0 && checkflg == 0 ){
                    setting( 2 );
                    if( tchflg == 0 && tch2 == false ){
                        if( gX >= 20 && gX < 440 && gY >= 130 && gY < 130+(60*2) ){
                            act = (gY-130)/60;
                            checkflg = 1;
                            ckflg = 1;
                            keyflg = 1;
                            gX = gY = 0;
                        }
                    }
                    if( keyflg != 1 && getKey == 25 ){
                        checkflg = 1;
                        ckflg = 1;
                        keyflg = 1;
                    }
                }
                setpnt("�~�N�d�l",0,0,0,act);
                setpnt("���J�d�l",1,0,3,act);
                break;

			case 2:
                if( moflg == 0 && checkflg == 0 ){
                    setting( 2 );
                    if( tchflg == 0 && tch2 == false ){
                        if( gX >= 20 && gX < 440 && gY >= 130 && gY < 130+(60*2) ){
                            act = (gY-130)/60;
                            checkflg = 1;
                            ckflg = 1;
                            keyflg = 1;
                            gX = gY = 0;
                        }
                    }
                    if( keyflg != 1 && getKey == 25 ){
                        checkflg = 1;
                        ckflg = 1;
                        keyflg = 1;
                    }
                }
                setpnt("�\��",0,0,0,act);
                setpnt("��\��",1,0,3,act);
                break;

            case 3:
                if( moflg == 0 && checkflg == 0 ){
                    setting( 2 );
                    if( tchflg == 0 && tch2 == false ){
                        if( gX >= 20 && gX < 440 && gY >= 130 && gY < 130+(60*2) ){
                            act = (gY-130)/60;
                            checkflg = 1;
                            ckflg = 1;
                            keyflg = 1;
                            gX = gY = 0;
                        }
                    }
                    if( keyflg != 1 && getKey == 25 ){
                        checkflg = 1;
                        ckflg = 1;
                        keyflg = 1;
                    }
                }
                setpnt("�\��",0,0,0,act);
                setpnt("��\��",1,0,3,act);
                break;

            case 4:
                if( moflg == 0 && checkflg == 0 ){
                    setting( 2 );
                    if( tchflg == 0 && tch2 == false ){
                        if( gX >= 20 && gX < 440 && gY >= 130 && gY < 130+(60*2) ){
                            act = (gY-130)/60;
                            checkflg = 1;
                            ckflg = 1;
                            keyflg = 1;
                            gX = gY = 0;
                        }
                    }
                    if( keyflg != 1 && getKey == 25 ){
                        checkflg = 1;
                        ckflg = 1;
                        keyflg = 1;
                    }
                }
                setpnt("12�L�[ (C4 �` B5)",0,0,0,act);
                setpnt("24�L�[ (C4 �` B6)",1,0,3,act);
                break;

            case 5:
                if( moflg == 0 && checkflg == 0 ){
                    setting( 4 );
                    if( tchflg == 0 && tch2 == false ){
                        if( gX >= 20 && gX < 440 && gY >= 130 && gY < 130+(60*4) ){
                            act = (gY-130)/60;
                            checkflg = 1;
                            ckflg = 1;
                            keyflg = 1;
                            gX = gY = 0;
                        }
                    }
                    if( keyflg != 1 && getKey == 25 ){
                        checkflg = 1;
                        ckflg = 1;
                        keyflg = 1;
                    }
                }
                setpnt("�W�� ( px)",0,0,0,act);
                setpnt("�ׂ� ( px)",1,0,0,act);
                setpnt("���� ( px)",2,0,0,act);
                setpnt("�ɑ�",3,0,3,act);
                break;

            case 6:

                if( moflg == 0 && checkflg == 0 ){
                    if( keyflg != 1 ){
                        if( getKey == 21 ){
                            act--;
                            cnt--;
                            keyflg = 1;
                        }
                        if( getKey == 23 ){
                            keyflg = 1;
                            act++;
                            cnt++;
                        }
                        if( getKey == 25 ){
                            checkflg = 1;
                            ckflg = 1;
                            keyflg = 1;
                        }
                    }
                    i = instset( 128 );
                    if( i == 1 ) xpostotal += 60;
                    if( i == 2 ) xpostotal -= 60;
                    if( tchflg == 0 && tch2 == false ){
                        if( gX >= 30 && gX < 450 && gY >= 100 && gY < 812 ){
                            act = (gY-130+xpostotal)/60;
                            checkflg = 1;
                            ckflg = 1;
                            keyflg = 1;
                            gX = gY = 0;
                        }
                    }
                }
                for(i=0;i<127;i++){
                    setpnt(instret[i],i,xpostotal,0,act);
                }
                setpnt(instret[127],i,xpostotal,3,act);
                break;
            }
            
            if( moflg == 0 && checkflg == 0 ){
                if( keyflg != 1 && getKey == 22 ){
                    moflg = 1;
                }
                if( tchflg == 0 ){
                    if( gX >= 0 && gX < 30 && gY >= 100 && gY < 814 ){
                        moflg = 1;
                        tchflg = 1;
                    }
                }
            } else if( moflg == 1 && checkflg == 0 ){
                if( keyflg != 1 ){
                    if( getKey == 24 ){
                        moflg = 0;
                    } else if( getKey == 25 ){
                        moselect( setpat );
                    }
                }
                if( tchflg == 0 ){
                    if( gX >= 20 && gX < 110 && gY >= 410 && gY < 458 ){
                        moselect( setpat );
                    } else if( gX >= 150 && gX < 480 && gY >= 100 && gY < 814 ){
                        moflg = 0;
                        tchflg = 1;
                    }
                }
            } else if( moflg == 0 && checkflg == 1 ){
                check( setpat, act );
            }

            modoru( moflg );
            scr( 0 );
            for( i=0;i<8;i++ ){
                g.setColor(Graphics.getColorOfRGB(0,0,0,255-i*32));
                g.fillRect(0,100+i,DISP_WIDTH,1);
            }
            menubar("�ݒ�");
            stbar();
            break;
            
        case 72:
            g.setColor(COLOR);
            g.drawString("�ύX���܂���",240 - font.getBBoxWidth("�ύX���܂���") / 2,427);
            if( cnt1 > 120 ){
                pattern = 5;
                break;
            }
            break;

        case 73:
            g.setColor(COLOR);
            g.drawString("�ύX���܂���",240 - font.getBBoxWidth("�ύX���܂���") / 2,427);
            if( cnt1 > 120 ){
                pattern = 61;
                break;
            }
            break;
            
        case 81:
	      	fileread("test.txt");
			at = 0;
			getdata();
            pattern = 83;
            break;
            
        case 83:// �^�b�`�L�[�{�[�h      
           if( tchflg == 0 ){
                if( gX >= 396 && gX < 476 && gY >= 4 && gY < 44 ){
                pattern = 41;
                gX = gY = 0;
                tchflg = 1;
                }
			}

            for(i=9;i>=1;i--){// �c������
                g.setColor(colorset(i));
  	            switch(tempPat[i]){
                case 1: pntLine( tempX[i], tempY[i], tempRad[i] ); break;
                case 2: pntCircle( tempX[i], tempY[i], tempR[i] ); break;
                case 3: pntTri( tempX[i], tempY[i], tempR[i], tempRad[i] ); break;
                case 4: pntBox( tempX[i], tempY[i], tempR[i], tempRad[i] ); break;
                case 5: pntStar( tempX[i], tempY[i], tempR[i], tempRad[i] ); break;
                default: break;
                }
            }
            for(i=9;i>0;i--){
                tempX[i] = tempX[i-1];
                tempY[i] = tempY[i-1];
                tempR[i] = tempR[i-1]+5;
                tempRad[i] = tempRad[i-1];
                tempPat[i] = tempPat[i-1];
            }

            randInit();// ��������
            if( getMenuFlag == 0 ){
                g.setColor(COLOR);
                
                // �L�[����̉��̍Đ�
                if( keyflg == 0 ){
                    if( getKey >= 0 && getKey < 12 ){
                        gX = randX + (getKey%3) * 160;
                        gY = randY + (getKey/3) * 214;
                        playSound( atplay[at] );
                        at++;
                    }
                }
                
                // �^�b�`���̉��̍Đ�
                if( tchflg == 1 ){
                    playSound( atplay[at] );
                    at++;
                }
                
                // �^�b�`���̐}�`�̕`��
                if( tchflg == 1 || tch ){
                    switch(randPat){
                    case 1: pntLine( gX , gY , randRad ); break;
                    case 2: pntCircle( gX , gY , randR ); break;
                    case 3: pntTri( gX , gY , randR, randRad ); break;
                    case 4: pntBox( gX , gY , randR, randRad ); break;
                    case 5: pntStar( gX , gY , randR, randRad ); break;
                    default: break;
                    }
                    tempX[0] = gX;
                    tempY[0] = gY;
                    tempR[0] = randR;
                    tempRad[0] = randRad;
                    tempPat[0] = randPat;
                } else if( keyflg == 0 ){
                    if( getKey >= 0 && getKey < 12 ){
                        switch(randPat){
                        case 1: pntLine( gX , gY , randRad ); break;
                        case 2: pntCircle( gX , gY , randR ); break;
                        case 3: pntTri( gX , gY , randR, randRad ); break;
                        case 4: pntBox( gX , gY , randR, randRad ); break;
                        case 5: pntStar( gX , gY , randR, randRad ); break;
                        default: break;
                        }
                        tempX[0] = gX;
                        tempY[0] = gY;
                        tempR[0] = randR;
                        tempRad[0] = randRad;
                        tempPat[0] = randPat;
                    }
                } else {
                    tempPat[0] = 0;
                }
            }
            if( mbshow != 1 ){
	           	g.drawImage(subbutton,396,4);
    	       	g.setColor(COLOR);
        	    g.drawString("MENU",436 - font.getBBoxWidth("MENU") / 2,35);
        	}
            getMenuFlag = 0;
            if( keyflg == 0 && getKey == 32 ){
                pattern = 41;
                gX = gY = 0;
                keyflg = 1;
            }
            tchflg = 0;
            keyflg = 1;
            break;
            
        case 101:
            //�I���V�[�N�G���X
            endscape();
            break;

        default:
            pattern = 5;
            break;
        }
        sysget();
        //developer();
        g.unlock(true);
    }

    public void mainmenu( int i ){
        if( i == 0 ) bt(12,514,BTACTB,"�u���̊y��v�N��",240);
        else bt(12,514,BTBIG,"�u���̊y��v�N��",240);
        if( i == 1 ) bt(12,614,BTACTB,"���ĂԂ胂�[�h",240);
        else bt(12,614,BTBIG,"���ĂԂ胂�[�h",240);
        if( i == 2 ) bt(12,714,BTACTS,"�I��",120);
        else bt(12,714,BTSMA,"�I��",120);
        if( i == 3 ) bt(246,714,BTACTS,"�ݒ�ύX",360);
        else bt(246,714,BTSMA,"�ݒ�ύX",360);
    }

    public void menuselect( int i ){
        if( i == 0 ){
            /* ���C���L�[�{�[�h��ʂ� */
            pattern = 31;
            getMenuFlag = 1;
            showKeyFlag = 1;
            for( i=0;i<9;i++ ){
                tempX[i] = 0;
                tempY[i] = 0;
                tempR[i] = 0;
                tempRad[i] = 0;
            }
        }
        if( i == 1 ){
            /* ���C���L�[�{�[�h��ʂ� */
            pattern = 81;
            getMenuFlag = 1;
            showKeyFlag = 1;
            at=0;
            for( i=0;i<9;i++ ){
                tempX[i] = 0;
                tempY[i] = 0;
                tempR[i] = 0;
                tempRad[i] = 0;
            }
        }
        if( i == 2 ){
            /* �I���V�[�N�G���X�� */
            pattern = 101;
        }
        if( i == 3 ){
            /* �ݒ��ʂ� */
            getMenuFlag = 1;
            gX = 0;
            gY = 0; 
            instFlag = 0;
            loadSetting();
            pattern = 61;
        }
    }

    public void submenu( int i ){
        if( i == 0 ) bt(12,229,BTACTS,"�ʏ탂�[�h",120);
        else bt(12,229,BTSMA,"�ʏ탂�[�h",120);
        if( i == 1 ) bt(12,229,BTACTS,"���ĂԂ�",360);
        else bt(246,229,BTSMA,"���ĂԂ�",360);
        if( i == 2 ) bt(12,331,BTACTB,"�ݒ�",240);
        else bt(12,331,BTBIG,"�ݒ�",240);
        if( i == 3 ) bt(12,433,BTACTB,"�^�C�g����",240);
        else bt(12,433,BTBIG,"�^�C�g����",240);
        if( i == 4 ) bt(12,535,BTACTB,"�I��",240);
        else bt(12,535,BTBIG,"�I��",240);
    }

    public void subselect( int i ){
        if( i == 0 ){
            pattemp = 31;
            pattern = 54;
            getMenuFlag = 1;
            gX = gY = 0;
        }
        if( i == 1 ){
        	pattemp = 81;
            pattern = 54;
            getMenuFlag = 1;
            gX = gY = 0;
        }
        if( i == 2 ){
            pattern = 61;
            getMenuFlag = 1;
            loadSetting();
            gX = gY = 0;
        }
        if( i == 3 ){
            pattern = 5;
            i = 0;
            gX = gY = 0;
        }
        if( i == 4 ){
            pattern = 101;
            gX = gY = 0;
        }
    }

    public void stbar(){
        g.drawImage(bar,0,814);
        if( sysmail >= 1 ){
            drawmail();
        }
        if( sysbatt != -1 ){
            drawbatt(sysbatt);
        } else {
            drawbatt(3);
        }
    }

    public int getact( int data ){
    	int ret=0;
        switch( data ){
            case 0:ret=charaSpec;break;
            case 1:ret=showKeyboard;break;
            case 2:ret=mbshow;break;
            case 3:ret=keyType;break;
            case 4:ret=kurow;break;
            case 5:ret=inst;break;
		}
		return ret;
	}

    public void check( int dat, int act ){
        for(i=1;i<5;i++){
            g.setColor(Graphics.getColorOfRGB(0,0,0,64));
            g.fillArc(380+i,180+i,40,40,0,90);
            g.fillArc(60+i,500+i,40,40,180,90);
            g.fillArc(380+i,500+i,40,40,270,90);
            g.fillRect(360+i,200+i,60,320);
            g.fillRect(80+i,520+i,320,20);
        }
        g.setColor(BLACK);
        g.fillArc(60,180,40,40,90,90);
        g.fillArc(380,180,40,40,0,90);
        g.fillArc(60,500,40,40,180,90);
        g.fillArc(380,500,40,40,270,90);
        g.fillRect(80,180,320,20);
        g.fillRect(60,200,360,320);
        g.fillRect(80,520,320,20);
        g.setColor(WHITE);
        g.fillArc(61,181,38,38,90,90);
        g.fillArc(381,181,38,38,0,90);
        g.fillRect(61,200,358,320);
        g.fillRect(80,181,320,19);
        g.fillRect(80,520,320,19);
        g.fillArc(61,501,38,38,180,90);
        g.fillArc(381,501,38,38,270,90);
        if( cact == 1 ){
            g.drawImage(cbt,75,460);
            g.drawImage(cbtact,245,460);
        } else {
            g.drawImage(cbtact,75,460);
            g.drawImage(cbt,245,460);
        }
        if( keyflg == 1 ){
            if( getKey == 22 ){
                cact = 1;
            }
            if( getKey == 24 ){
                cact = 0;
            }
        }
        if( tchflg != 1 && ckflg == 0 ){
            if( gX >= 75 && gX < 235 && gY >= 460 && gY < 520 ){
                cact = 0;
                checkflg = 0;
                gX = gY = 0;
            }
            if( gX >= 245 && gX < 405 && gY >= 460 && gY < 520 ){
                switch(dat-1){
                case 0:charaSpec=act;break;
                case 1:showKeyboard=act;break;
                case 2:mbshow=act;break;
                case 3:keyType=act;break;
                case 4:kurow=act;break;
                case 5:inst=act;break;
                }
                checkflg = 0;
                pattern = 73;
                gX = gY = 0;
                saveSetting();
            }
        }
        if( keyflg != 1 && getKey == 25 && ckflg == 0 ){
            if( cact == 1 ){
                switch(dat-1){
                case 0:charaSpec=act;break;
                case 1:showKeyboard=act;break;
                case 2:showKeyboard=act;break;
                case 3:keyType=act;break;
                case 4:kurow=act;break;
                case 5:inst=act;break;
                }
                checkflg = 0;
                pattern = 73;
                saveSetting();
                gX = gY = 0;
            } else {
                cact = 0;
                checkflg = 0;
                pattern = 61;
                gX = gY = 0;
            }
        }
        g.setColor(BLACK);
            switch( dat-1 ){
            case 0:
                ck = "�L�����ݒ�";
                if( act == 0 ){
                    ck2 = "�u�����~�N�v";
                } else {
                    ck2 = "�u�������J�v";
                }
                break;
            case 1:
                ck = "�L�[�{�[�h";
                if( act == 0 ){
                    ck2 = "�u�\������v";
                } else {
                    ck2 = "�u�\�����Ȃ��v";
                }
                break;
            case 2:
                ck = "MENU�{�^��";
                if( act == 0 ){
                    ck2 = "�u�\������v";
                } else {
                    ck2 = "�u�\�����Ȃ��v";
                }
                break;
            case 3:
                ck = "�L�[�̐�";
                if( act == 0 ){
                    ck2 = "�u12�L�[�v";
                } else {
                    ck2 = "�u24�L�[�v";
                }
                break;
            case 4:
                ck = "�����Ղ̕�";
                if( act == 0 ){
                    ck2 = "�u�W���v";
                } else if( act == 1 ){
                    ck2 = "�u�ׂ߁v";
                } else if( act == 2 ){
                    ck2 = "�u���߁v";
                } else if( act == 3 ){
                    ck2 = "�u�ɑ��v";
                }
                break;
            case 5:
                ck = "����";
                ck2 = "�u" + instret[ act ] + "�v";
                break;
            }
        g.drawString(ck+"��",240 - font.getBBoxWidth(ck+"��")/2,250);
        g.drawString(ck2+"��",240 - font.getBBoxWidth(ck2+"��")/2,300);
        g.drawString("�ύX���܂����H",240 - font.getBBoxWidth("�ύX���܂����H")/2,350);
        g.drawString("�L�����Z��",155 - font.getBBoxWidth("�L�����Z��")/2,500);
        g.drawString("����",325 - font.getBBoxWidth("����")/2,500);
        ckflg = 0;
    }

    public void modoru( int flg ){
        if( flg == 1 ){
            g.setColor(GRAY);
            g.fillRect(0,100,75,754);
            g.setColor(BGRAY);
            g.fillRect(74,100,1,754);
            g.setColor(BLACK);
            g.fillRect(75,100,75,754);
            g.setColor(LGRAY);
            g.drawLine(150,100,150,854);
            g.drawLine(148,100,148,854);
            g.setColor(WHITE);
            g.drawLine(149,100,149,854);
            arrow( 137,427,6,flg,WHITE );
            g.drawImage(modo,20,410);
            for( i=0;i<4;i++ ){
                g.setColor(Graphics.getColorOfRGB(0,0,0,255-i*64));
                g.fillRect(151+i,100,1,754);
            }
        } else {
            g.setColor(GRAY);
            g.fillRect(0,100,6,754);
            g.setColor(BGRAY);
            g.fillRect(6,100,1,754);
            g.setColor(BLACK);
            g.fillRect(7,100,7,754);
            g.setColor(LGRAY);
            g.drawLine(14,100,14,854);
            g.setColor(WHITE);
            arrow( 3,427,6,flg,WHITE );
            for( i=0;i<4;i++ ){
                g.setColor(Graphics.getColorOfRGB(0,0,0,255-i*64));
                g.fillRect(15+i,100,1,754);
            }
        }
    }

    public void scr( int flg ){
        if( flg == 1 ){
            g.setColor(GRAY);
            g.fillRect(0,100,75,754);
            g.setColor(BGRAY);
            g.fillRect(74,100,1,754);
            g.setColor(BLACK);
            g.fillRect(75,100,75,754);
            g.setColor(LGRAY);
            g.drawLine(150,100,150,854);
            g.drawLine(148,100,148,854);
            g.setColor(WHITE);
            g.drawLine(149,100,149,854);
            arrow( 137,427,6,flg,WHITE );
            g.drawImage(modo,20,410);
            for( i=0;i<4;i++ ){
                g.setColor(Graphics.getColorOfRGB(0,0,0,255-i*64));
                g.fillRect(151+i,100,1,754);
            }
        } else {
            g.setColor(LGRAY);
            g.fillRect(474,100,6,754);
            g.setColor(GRAY);
            g.fillRect(473,100,1,754);
            g.setColor(GRAY);
            g.fillRect(466,100,7,754);
            g.setColor(WHITE);
            g.drawLine(465,100,465,854);
        }
    }

    public void arrow( int x, int y, int size, int m, int color ){
        g.setColor( color );
        if( m == 0 ){//�E����
            //��_
            g.drawLine(x,y,x+size,y+size);
            g.drawLine(x-1,y,x+size-1,y+size);
            g.drawLine(x,y-1,x+size+1,y+size);
            g.drawLine(x+size,y+size,x,y+size+size);
            g.drawLine(x+size-1,y+size,x-1,y+size+size);
            g.drawLine(x+size+1,y+size,x,y+size+size+1);
        } else {
            g.drawLine(x,y+size,x+size,y);
            g.drawLine(x-1,y+size,x+size,y-1);
            g.drawLine(x+1,y+size,x+size+1,y);
            g.drawLine(x,y+size,x+size,y+size+size);
            g.drawLine(x-1,y+size,x+size,y+size+size+1);
            g.drawLine(x+1,y+size,x+size+1,y+size+size);
        }
    }

    public void setpnt( String str, int p, int i, int lr, int act ){
        if( p == 0 ){
            g.setColor(BLACK);
            g.fillArc(30,129-i,40,40,90,90);
            g.fillArc(410,129-i,40,40,0,90);
            g.fillRect(30,149-i,420,41);
            g.fillRect(50,129-i,380,20);
            if( p == act ){
                g.setColor(ORANGE);
            } else {
                g.setColor(WHITE);
            }
            g.fillArc(31,130-i,38,38,90,90);
            g.fillArc(411,130-i,38,38,0,90);
            g.fillRect(31,149-i,418,40);
            g.fillRect(50,130-i,380,19);
        } else if( lr == 3 ){
            g.setColor(BLACK);
            g.fillArc(30,p*60+149-i,40,40,180,90);
            g.fillArc(410,p*60+149-i,40,40,270,90);
            g.fillRect(30,p*60+129-i,420,41);
            g.fillRect(50,p*60+169-i,380,20);
            if( p == act ){
                g.setColor(ORANGE);
            } else {
                g.setColor(WHITE);
            }
            g.fillArc(31,p*60+150-i,38,38,180,90);
            g.fillArc(411,p*60+150-i,38,38,270,90);
            g.fillRect(31,p*60+130-i,418,40);
            g.fillRect(50,p*60+169-i,380,19);
        } else {
            g.setColor(BLACK);
            g.fillRect(30,p*60+130-i,420,60);
            if( p == act ){
                g.setColor(ORANGE);
            } else {
                g.setColor(WHITE);
            }
            g.fillRect(31,p*60+130-i,418,59);
        }
        g.setColor(BLACK);
        if( lr == 1 ){
            arrow(400,154+60*p,6,0,BLACK);
        }
        g.drawString(str,60,p*60+170-i);
    }
    
    public void menubar( String menu ){
        g.drawImage(menubar,0,0);
        g.setColor(WHITE);
        g.drawString(menu,65,60);
    }
    
    public void drawbatt( int a ){
        int battx[] = {430,434,471};
        int batty[] = {820,826,839,845};
        g.setColor(WHITE);
        g.fillRect(battx[0],batty[1]+1,battx[1]-battx[0],batty[2]-batty[1]-1);
        g.drawRect(battx[1],batty[0],battx[2]-battx[1],batty[3]-batty[0]);
        g.drawRect(battx[1]+1,batty[0]+1,battx[2]-battx[1]-2,batty[3]-batty[0]-2);
        g.fillRect(battx[2]-3-a*10,batty[0]+4,a*10,18);
    }
    
    public void drawmail(){
        g.setColor(LGRAY);
        g.drawLine(381,820,397,836);
        g.drawLine(380,821,397,838);
        g.drawLine(397,836,412,821);
        g.drawLine(397,838,413,822);
        g.setColor(WHITE);
        g.drawLine(380,820,397,837);
        g.drawLine(397,837,413,821);
        g.drawRect(380,820,34,25);
        g.drawRect(381,821,32,23);
    }

    public void setting( int n ){
        if( keyflg != 1 ){
            if( getKey == 21 ){
                act--;
                keyflg = 1;
            }
            if( getKey == 23 ){
                keyflg = 1;
                act++;
            }
        }
        if( act < 0 ) act = 0;
        if( act >= n ) act = n-1;
    }

    public int instset( int n ){
        int ret=0;
        if( act < 0 ) act = 0;
        if( act >= n ) act = n-1;
        if( cnt < 0 ){
            cnt = 0;
            ret = 2;
        }
        if( cnt >= 11 ){
            cnt = 10;
            ret = 1;
        }
        return ret;
    }

    public void moselect( int pat ){
        if( setpat != 0 ){
            pattern = 61;
            tchflg = 1;
            keyflg = 1;
            setpat = 0;
        } else {
            pattern = 72;
            saveSetting();
            cnt1 = 0;
        }
        moflg = 0;
        gX = gY = 0;
    }

	public void getdata(){
        try {
            byte[] data = read();
            
            Dialog d = new Dialog(Dialog.DIALOG_INFO, "Information");
            d.setText("�ǂݍ��ݐ���\n" + new String(data));
            d.show();
        } catch (Exception e) {
            Dialog d = new Dialog(Dialog.DIALOG_INFO, "Exception");
            d.setText(e.toString());
            d.show();
        }
        StringTokenizer st = new StringTokenizer(data);
        while (st.hasMoreTokens()) {
            playbuf[i] = Integer.parseInt(st.nextToken());
            i++;
        }
	}

    ////////////////////////////////////////////////////////////////////////////
    //  ���̕\��
    ////////////////////////////////////////////////////////////////////////////
    public void pntLine( int posX, int posY, int rad){
        int i, x1, x2, y1, y2, x, y;
        x2 = x1 = posX;
        y2 = y1 = posY;
        x = (int)(Math.cos(radDeg(rad))*5);
        y = (int)(Math.sin(radDeg(rad))*5);
        while( y1> -5 && y1< DISP_HEIGHT+5 && x1> -5 && x1< DISP_WIDTH+5 ){
            x1 = x1 + x;
            y1 = y1 + y;
        }
        while( y2> -5 && y2< DISP_HEIGHT+5 && x2> -5 && x2< DISP_WIDTH+5 ){
            x2 = x2 - x;
            y2 = y2 - y;
        }
        boxX[0] = x1 + (int)(Math.cos(radDeg(rad-90))*5);
        boxY[0] = y1 + (int)(Math.sin(radDeg(rad-90))*5);
        boxX[1] = x1 + (int)(Math.cos(radDeg(rad+90))*5);
        boxY[1] = y1 + (int)(Math.sin(radDeg(rad+90))*5);
        boxX[2] = x2 + (int)(Math.cos(radDeg(rad+90))*5);
        boxY[2] = y2 + (int)(Math.sin(radDeg(rad+90))*5);
        boxX[3] = x2 + (int)(Math.cos(radDeg(rad-90))*5);
        boxY[3] = y2 + (int)(Math.sin(radDeg(rad-90))*5);
        g.fillPolygon( boxX, boxY, 4 );    
    }

    ////////////////////////////////////////////////////////////////////////////
    //  �~�̕\��
    ////////////////////////////////////////////////////////////////////////////
    public void pntCircle( int posX, int posY, int r ){
        r = r * 5 + 30;
        g.fillArc( posX-r, posY-r, r*2+1, r*2+1, 0, 360 );
        g.setColor(BLACK);
        g.fillArc( posX-r+10, posY-r+10,  r*2-19, r*2-19, 0, 360 );
    }

    ////////////////////////////////////////////////////////////////////////////
    //  �O�p�`�̕\��
    ////////////////////////////////////////////////////////////////////////////
    public void pntTri( int posX, int posY, int r1, int rad ){
        int r;
        // �O�s�̌���
        r = r1 * 5 + 30;
        polix[0] = posX + (int)(Math.sin(radDeg(rad)) * r);
        poliy[0] = posY + (int)(Math.cos(radDeg(rad)) * r);
        polix[1] = posX + (int)(Math.sin(radDeg(rad-120)) * r);
        poliy[1] = posY + (int)(Math.cos(radDeg(rad-120)) * r);
        polix[2] = posX + (int)(Math.sin(radDeg(rad+120)) * r);
        poliy[2] = posY + (int)(Math.cos(radDeg(rad+120)) * r);
        g.fillPolygon( polix, poliy, 3 );

        // ���������œh��Ԃ�
        g.setColor(BLACK);
        r = r1 * 5 + 20;
        polix[0] = posX + (int)(Math.sin(radDeg(rad)) * r);
        poliy[0] = posY + (int)(Math.cos(radDeg(rad)) * r);
        polix[1] = posX + (int)(Math.sin(radDeg(rad-120)) * r);
        poliy[1] = posY + (int)(Math.cos(radDeg(rad-120)) * r);
        polix[2] = posX + (int)(Math.sin(radDeg(rad+120)) * r);
        poliy[2] = posY + (int)(Math.cos(radDeg(rad+120)) * r);
        g.fillPolygon( polix, poliy, 3 );
    }

    ////////////////////////////////////////////////////////////////////////////
    //  �l�p�`�̕\��
    ////////////////////////////////////////////////////////////////////////////
    public void pntBox( int posX, int posY, int r1, int rad ){
        int i, r;
        r=0;
        r=r1*5+30;
        for( i=0;i<4;i++ ){
            polix[i] = posX + (int)(Math.sin(radDeg(rad+(i*90))) * r);
            poliy[i] = posY + (int)(Math.cos(radDeg(rad+(i*90))) * r);
        }
        g.fillPolygon( polix, poliy, 4 );

        //���œh��Ԃ�
        g.setColor(BLACK);
        r=r1*5+20;
        for( i=0;i<4;i++ ){
            polix[i] = posX + (int)(Math.sin(radDeg(rad+(i*90))) * r);
            poliy[i] = posY + (int)(Math.cos(radDeg(rad+(i*90))) * r);
        }
        g.fillPolygon( polix, poliy, 4 );

    }    

    ////////////////////////////////////////////////////////////////////////////
    //  ���^�̕\��
    ////////////////////////////////////////////////////////////////////////////
    public void pntStar( int posX, int posY, int r1, int rad ){
        int i,r;
        r=0;
        for( i=0;i<10;i++){
            if( i%2==0 ) r=r1*5+30;
            if( i%2==1 ) r=r1*3+20;
            polix[i] = posX + (int)(Math.sin(radDeg(rad+(i*36))) * r);
            poliy[i] = posY + (int)(Math.cos(radDeg(rad+(i*36))) * r);
        }
        g.fillPolygon( polix, poliy, 10 );

        g.setColor(BLACK);
        for( i=0;i<10;i++){
            if( i%2==0 ) r=r1*5+20;
            if( i%2==1 ) r=r1*3+10;
            polix[i] = posX + (int)(Math.sin(radDeg(rad+(i*36))) * r);
            poliy[i] = posY + (int)(Math.cos(radDeg(rad+(i*36))) * r);
        }
        g.fillPolygon( polix, poliy, 10 );
    }

    ////////////////////////////////////////////////////////////////////////////
    //  Deg��Rad�ϊ�
    ////////////////////////////////////////////////////////////////////////////
    public double radDeg( int s ){
        double radeon;
        radeon = s * Math.PI / 180;
        return radeon;
    }

    ////////////////////////////////////////////////////////////////////////////
    //  ��������
    ////////////////////////////////////////////////////////////////////////////
    public void randInit() {
        Random rnd = new Random();
        randPat = Math.abs(rnd.nextInt())%5+1;//�\���p�^�[��
        randR = Math.abs(rnd.nextInt())%10;//���a�Ȃ�R�̐ݒ�
        randRad = Math.abs(rnd.nextInt())%180;//�p�x�̐ݒ�
        randX = Math.abs(rnd.nextInt())%160;
        randY = Math.abs(rnd.nextInt())%214;
    }
    
    public void sysget(){
        sysmail = PhoneSystem.getAttribute(PhoneSystem.DEV_MAILBOX);
        sysant = PhoneSystem.getAttribute(PhoneSystem.DEV_SIGNAL_STATE);
        sysman = PhoneSystem.getAttribute(PhoneSystem.DEV_MANNER);
        sysbatt = PhoneSystem.getAttribute(PhoneSystem.DEV_BATTERY_LEVEL);
    }
    
    public void touchKeyborad() {
        int i;
        g.setColor(LGRAY);
        for(i=1;i<14;i++){
            g.drawLine(0,i*61,240,i*61);
            if( i == 3 || i == 7 || i == 10 || i == 14 ){
                g.drawLine(241,i*61,480,i*61);
            } else {
                g.drawLine(240,i*61-KURO,240,i*61+KURO);
                g.drawLine(240,i*61-KURO,480,i*61-KURO);
                g.drawLine(240,i*61+KURO,480,i*61+KURO);
            }
        }
    }

    public void touchKeyborad2() {
        int i;
        g.setColor(LGRAY);
        for(i=1;i<7;i++){
            g.drawLine(0,i*122,240,i*122);
            if( i == 3 || i == 7 ){
                g.drawLine(241,i*122,480,i*122);
            } else {
                g.drawLine(240,i*122-KURO2,240,i*122+KURO2);
                g.drawLine(240,i*122-KURO2,480,i*122-KURO2);
                g.drawLine(240,i*122+KURO2,480,i*122+KURO2);
            }
        }
    }

    public int keyPos( int xx, int yy, int show ){
        int i,dat;
        dat = 0;
        if( xx < 240 ){
            i = yy/61;
            switch( i ){
                case 0:dat=60;break;
                case 1:dat=62;break;
                case 2:dat=64;break;
                case 3:dat=65;break;
                case 4:dat=67;break;
                case 5:dat=69;break;
                case 6:dat=71;break;
                case 7:dat=72;break;
                case 8:dat=74;break;
                case 9:dat=76;break;
                case 10:dat=77;break;
                case 11:dat=79;break;
                case 12:dat=81;break;
                case 13:dat=83;break;
                default:dat=0;break;

            }
        } else if( xx >= 240) {
            if( gY >= 0 && gY < 61-KURO ){
                dat = 60;/* �h */
            } else if( gY >= 61-KURO && gY < 61+KURO ){
                dat = 61;/* �h�� */
            } else if( gY >= 61+KURO && gY < 122-KURO ){
                dat = 62;/* �� */
            } else if( gY >= 122-KURO && gY < 122+KURO ){
                dat = 63;/* ���� */
            } else if( gY >= 122+KURO && gY < 183){
                dat = 64;/* �~ */
            } else if( gY >= 183 && gY < 244-KURO){
                dat = 65;/* �t�@ */
            } else if( gY >= 244-KURO && gY < 244+KURO ){
                dat = 66;/* �t�@�� */
            } else if( gY >= 244+KURO && gY < 305-KURO ){
                dat = 67;/* �\ */
            } else if( gY >= 305-KURO && gY < 305+KURO ){
                dat = 68;/* �\�� */
            } else if( gY >= 305+KURO && gY < 366-KURO){
                dat = 69;/* �� */
            } else if( gY >= 366-KURO && gY < 366+KURO){
                dat = 70;/* ���� */
            } else if( gY >= 366+KURO && gY < 427 ){
                dat = 71;/* �V */
            } else if( gY >= 427 && gY < 488-KURO ){
                dat = 72;/* �h */
            } else if( gY >= 488-KURO && gY < 488+KURO ){
                dat = 73;/* �h�� */
            } else if( gY >= 488+KURO && gY < 549-KURO ){
                dat = 74;/* �� */
            } else if( gY >= 549-KURO && gY < 549+KURO ){
                dat = 75;/* ���� */
            } else if( gY >= 549+KURO && gY < 610 ){
                dat = 76;/* �~ */
            } else if( gY >= 610 && gY < 671-KURO ){
                dat = 77;/* �t�@ */
            } else if( gY >= 671-KURO && gY < 671+KURO ){
                dat = 78;/* �t�@�� */
            } else if( gY >= 671+KURO && gY < 732-KURO ){
                dat = 79;/* �\ */
            } else if( gY >= 732-KURO && gY < 732+KURO ){
                dat = 80;/* �\�� */
            } else if( gY >= 732+KURO && gY < 793-KURO ){
                dat = 81;/* �� */
            } else if( gY >= 793-KURO && gY < 793+KURO ){
                dat = 82;/* ���� */
            } else if( gY >= 793+KURO && gY < 854 ){
                dat = 83;/* �V */
            }
        } else { 
            dat = 0;
        }
        return dat;
    }

    public int keyPos2( int xx, int yy, int show ){
        int i,dat;
        dat = 0;
        if( xx < 240 ){
            i = yy/122;
            switch( i ){
                case 0:dat=60;break;
                case 1:dat=62;break;
                case 2:dat=64;break;
                case 3:dat=65;break;
                case 4:dat=67;break;
                case 5:dat=69;break;
                case 6:dat=71;break;
                default:dat=0;break;

            }
        } else if( xx >= 240) {
            if( gY >= 0 && gY < 122-KURO2 ){
                dat = 60;/* �h */
            } else if( gY >= 122-KURO2 && gY < 122+KURO2 ){
                dat = 61;/* �h�� */
            } else if( gY >= 122+KURO2 && gY < 244-KURO2 ){
                dat = 62;/* �� */
            } else if( gY >= 244-KURO2 && gY < 244+KURO2 ){
                dat = 63;/* ���� */
            } else if( gY >= 244+KURO2 && gY < 366){
                dat = 64;/* �~ */
            } else if( gY >= 366 && gY < 488-KURO2){
                dat = 65;/* �t�@ */
            } else if( gY >= 488-KURO2 && gY < 488+KURO2 ){
                dat = 66;/* �t�@�� */
            } else if( gY >= 488+KURO2 && gY < 610-KURO2 ){
                dat = 67;/* �\ */
            } else if( gY >= 610-KURO2 && gY < 610+KURO2 ){
                dat = 68;/* �\�� */
            } else if( gY >= 610+KURO2 && gY < 732-KURO2 ){
                dat = 69;/* �� */
            } else if( gY >= 732-KURO2 && gY < 732+KURO2 ){
                dat = 70;/* ���� */
            } else if( gY >= 732+KURO2 && gY < 854 ){
                dat = 71;/* �V */
            }
        } else { 
            dat = 0;
        }
        return dat;
    }

	public void fileread( String file ){
		filename = file;
        try {
            data = read();
            
            Dialog d = new Dialog(Dialog.DIALOG_INFO, "Information");
            d.show();
        } catch (Exception e) {
            Dialog d = new Dialog(Dialog.DIALOG_INFO, "Exception");
            d.setText(e.toString());
            d.show();
        }
    }

    public void processEvent(int type, int param) {
        // �ȉ��̓^�b�`�p�l���̃C�x���g����
        // �^�b�`�J�n
        switch( type ){
        case Display.TOUCH_PRESSED_EVENT://�����ꂽ
            tch = false;
            tch2 = false;
            tchflg = 1;
            gY = TouchDevice.getY();
            gX = TouchDevice.getX();
            break;

        case Display.TOUCH_MOVEDSTART_EVENT://������
            tch = true;
            tch2 = true;
            xpos = gY;
            break;

        case Display.TOUCH_MOVEDEND_EVENT://�~�܂���
            tch = false;
            break;

        case Display.TOUCH_RELEASED_EVENT://���ꂽ
            tch = false;
            tchflg = 0;
            break;

        // �ȉ��L�[�p�b�h�ł̏���
        case Display.KEY_PRESSED_EVENT:
            keyflg = 1;
            getKey = keyOut(param);
            break;

        case Display.KEY_RELEASED_EVENT:
            keyflg = 0;
            break;

        default:
            break;
        }

        //�\�t�g�L�[1�ŏI��
        if (type == Display.KEY_RELEASED_EVENT) {
            if (param == Display.KEY_SOFT1) {
            	endscape();
            }
        }
    }
    
    private void playSound( int playkey ) {
        int i = apIndex;
        int temp;
        temp = (vol*16);
        if( temp >= 128 ){
        	temp = 127; 
       	}
        try {
            MIDI_DATA[28] = (byte)inst;
            MIDI_DATA[31] = (byte)playkey;
            MIDI_DATA[32] = (byte)temp;
            MediaSound ms = MediaManager.getSound(MIDI_DATA);
            ms.use();
            try {
                ap[i].stop();
            } catch (Exception e) {}
            ap[i].setSound(ms);
            ap[i].play();
        if ( ++apIndex >= ap.length ) apIndex = 0;
        } catch (Exception e) {}
    }

    private void bt( int x, int y, int size, String str, int stp ){
        if( size == 0 ){
            g.drawImage(lbutton,x,y);
            g.setColor(WHITE);
        } else if( size == 1 ){
            g.drawImage( sbutton,x,y);
            g.setColor(WHITE);
        } else if( size == 2 ){
            g.drawImage( actlbt,x,y);
            g.setColor(BLACK);
        } else if( size == 3 ){
            g.drawImage( actsbt,x,y);
            g.setColor(BLACK);
        }
        g.drawString(str,stp-font.getBBoxWidth(str)/2,y+(88+FONT_BASE)/2);
    }
    
    private void setbt( int x, int y, int size, int pos ){
        int i;
        g.setColor(LGRAY);
        g.fillRect(x-5,y,120*size+10,60);
        g.setColor(BGRAY);
        g.fillRect(x,y+5,120*size,50);
        g.setColor(COLOR);
        g.fillRect(x+120*(pos-1),y+5,120,50);

    }
    
    // �ݒ�ۑ�
    private boolean saveSetting() {
        OutputStream os = null;
        DataOutputStream dos = null;
    
        try {
            os = Connector.openOutputStream("scratchpad:///0");
            dos = new DataOutputStream(os);
      
            // �L�����d�l�ݒ�
            dos.writeByte(charaSpec);
            // �L�[�{�[�h�\���ݒ�
            dos.writeByte(showKeyboard);
            // MENU�{�^���\���ݒ�
            dos.writeByte(mbshow);
            // �L�[�{�[�h�̕�
            dos.writeByte(keyType);
            // Instrumental�ݒ�(�o�C�g�z��H)
            dos.writeByte(inst);
            // ���Ղ̕�
            dos.writeByte(kurow);
            // ����
            dos.writeByte(vol);

            // �ۑ�����
            return true;
        } catch (Exception e) {
            // �ۑ����s
            return false;
        } finally {
            try {
            dos.close();
        } catch (Exception e) {}
            try {
                os.close();
            } catch (Exception e) {}
        }
    }
    
    // �����ݒ�t���O����
    private boolean savenewdata() {
        OutputStream os = null;
        DataOutputStream dos = null;
    
        try {
            os = Connector.openOutputStream("scratchpad:///1");
            dos = new DataOutputStream(os);
      
            // �t���O����
            dos.writeByte(newdata);

            // �ۑ�����
            return true;
        } catch (Exception e) {
            // �ۑ����s
            return false;
        } finally {
            try {
            dos.close();
        } catch (Exception e) {}
            try {
                os.close();
            } catch (Exception e) {}
        }
    }

    // �ݒ�Ǎ�
    private boolean loadSetting() {
        InputStream is = null;
        DataInputStream dis = null;
    
        try {
            is = Connector.openInputStream("scratchpad:///0");
            dis = new DataInputStream(is);
      
            // �L�����d�l�ݒ�
            charaSpec = dis.readByte();
            // �L�[�{�[�h�\���ݒ�
            showKeyboard = dis.readByte();
            // ���j���[�{�^���̕\���E��\��
            mbshow = dis.readByte();
            // �L�[�{�[�h�̕�
            keyType = dis.readByte();
            // Instrumental�ݒ�(�o�C�g�z��H)
            inst = dis.readByte();
            // ���Ղ̕�
            kurow = dis.readByte();
            // ����
            vol = dis.readByte();

            // �Ǎ�����
            return true;
        } catch (Exception e) {
            // �Ǎ����s
            return false;
        } finally {
            try {
                dis.close();
            } catch (Exception e) {}
            try {
                is.close();
            } catch (Exception e) {}
        }
    }

    void write(byte[] data) throws IOException {
        Folder folder = null;
        File file = null;
        FileEntity entity = null;
        OutputStream os = null;
        
        // StorageDevice�C���X�^���X�𖢎擾�̏ꍇ�͎擾
        // ������deviceName��"/ext0"�ŌŒ�
        if ( sd == null ) {
            sd = StorageDevice.getInstance("/ext0");
        }
        
        // �t�H���_/�t�@�C���̃A�N�Z�X����\���C���X�^���X���쐬
        // �A�N�Z�X�����Ȃ�������A�v���̂݋��L��
        if ( sat == null ) {
            sat = StarStorageService.getAccessToken(0x00, StarStorageService.SHARE_APPLICATION);
        }
        
        // ���A�v����SD�t�H���_���擾
        folder = sd.getFolder(sat);
        
        try {
            // �t�@�C����V�K�쐬
            file = folder.createFile(filename);
        } catch (FileNotAccessibleException fnae) {
            // ���ɓ����t�@�C��������ꍇ�̓t�@�C���擾�ɐ؂�ւ�
            if ( fnae.getStatus() == FileNotAccessibleException.ALREADY_EXISTS ) {
                file = folder.getFile(filename);
            } else {
                throw fnae;
            }
        }
        
        try {
            // �t�@�C���̎��̂��擾
            // �������݃��\�b�h�Ȃ̂�WRITE_ONLY�ŊJ��
            entity = file.open(File.MODE_WRITE_ONLY);
            
            // �������ݗp�X�g���[���擾
            os = entity.openOutputStream();
            
            // �X�g���[���ɏ�������
            os.write(data);
        } finally {
            // �����̐���/���s�Ɋւ�炸�A�I�[�v���������̂��N���[�Y����
            try {
                os.close();
            } catch (Throwable t) {}
            try {
                entity.close();
            } catch (Throwable t) {}
        }
    }

    byte[] read() throws IOException {
        Folder folder = null;
        File file = null;
        FileEntity entity = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        byte[] buf = new byte[1024];
        int size = 0;
        
        // StorageDevice�C���X�^���X�𖢎擾�̏ꍇ�͎擾
        // ������deviceName��"/ext0"�ŌŒ�
        if ( sd == null ) {
            sd = StorageDevice.getInstance("/ext0");
        }
        
        // �t�H���_/�t�@�C���̃A�N�Z�X����\���C���X�^���X���쐬
        // �A�N�Z�X�����Ȃ�������A�v���̂݋��L��
        if ( sat == null ) {
            sat = StarStorageService.getAccessToken(0x00, StarStorageService.SHARE_APPLICATION);
        }
        
        // ���A�v����SD�t�H���_���擾
        folder = sd.getFolder(sat);
        
        // �t�@�C�����擾
        file = folder.getFile(filename);
        
        try {
            // �t�@�C���̎��̂��擾
            // �ǂݍ��݃��\�b�h�Ȃ̂�READ_ONLY�ŊJ��
            entity = file.open(File.MODE_READ_ONLY);
            
            // �ǂݍ��ݗp�X�g���[���擾
            is = entity.openInputStream();
            
            // �ǂݍ��݌��ʂ��ꎞ�ۑ����Ă����o�C�g�z��o�̓X�g���[�����쐬
            baos = new ByteArrayOutputStream();
            
            // �t�@�C���S�̂�ǂݍ���Ńo�C�g�z��o�̓X�g���[���ɒ~��
            while( (size = is.read(buf, 0, buf.length)) > 0 ) {
                baos.write(buf, 0, size);
            }
            
            // �~�ς������ʂ�byte[]�ɕϊ����ĕԋp
            return baos.toByteArray();
        } finally {
            // �����̐���/���s�Ɋւ�炸�A�I�[�v���������̂��N���[�Y����
            try {
                baos.close();
            } catch (Throwable t) {}
            try {
                is.close();
            } catch (Throwable t) {}
            try {
                entity.close();
            } catch (Throwable t) {}
        }
    }

    // �����ݒ�t���O�Ǎ�
    private boolean loadnewdata() {
        InputStream is = null;
        DataInputStream dis = null;
    
        try {
            is = Connector.openInputStream("scratchpad:///1");
            dis = new DataInputStream(is);
      
            // �L�����d�l�ݒ�
            newdata = dis.readByte();

            // �Ǎ�����
            return true;
        } catch (Exception e) {
            // �Ǎ����s
            return false;
        } finally {
            try {
                dis.close();
            } catch (Exception e) {}
            try {
                is.close();
            } catch (Exception e) {}
        }
    }


    private int colorset( int n ){
        int c;
        if( charaSpec == RUKA ){
            c = Graphics.getColorOfRGB(216-n*24,189-n*21,189-n*21);
        } else {
            c = Graphics.getColorOfRGB(117-n*13,189-n*21,180-n*20);
        }
        return c;
    }
    
    private void developer(){
        String x,y,t,k;
        g.drawString("moflg=",10,634);
        g.drawString("checkflg=",10,664);
        g.drawString("keyType=",10,694);
        g.drawString("tchflg=",10,724);
        g.drawString("keyflg=",10,754);
        g.drawString("gX=",10,814);
        g.drawString("gY=",10,844);
        
        g.drawString(moflg+"",10+font.getBBoxWidth("moflg="),634);
        g.drawString(checkflg+"",10+font.getBBoxWidth("checkflg="),664);
        g.drawString(keyType+"",10+font.getBBoxWidth("keyType="),694);
        g.drawString(tchflg+"",10+font.getBBoxWidth("tchflg="),724);
        g.drawString(keyflg+"",10+font.getBBoxWidth("keyflg="),754);
        g.drawString(evStus,10,784);
        g.drawString(gX+"",10+font.getBBoxWidth("gX="),814);
        g.drawString(gY+"",10+font.getBBoxWidth("gY="),844);
    }
    
    int keyOut(int keyCode){
        switch(keyCode){
            case Display.KEY_0:return 10;
            case Display.KEY_1:return 0;
            case Display.KEY_2:return 1;
            case Display.KEY_3:return 2;
            case Display.KEY_4:return 3;
            case Display.KEY_5:return 4;
            case Display.KEY_6:return 5;
            case Display.KEY_7:return 6;
            case Display.KEY_8:return 7;
            case Display.KEY_9:return 8;
            case Display.KEY_ASTERISK:return 9;
            case Display.KEY_POUND:return 11;
            case Display.KEY_LEFT:return 24;//���L�[
            case Display.KEY_UP:return 21;//���L�[
            case Display.KEY_RIGHT:return 22;//���L�[
            case Display.KEY_DOWN:return 23;//���L�[
            case Display.KEY_SELECT:return 25;//����L�[
            case Display.KEY_SOFT1:return 31;//�\�t�g�P�L�[;
            case Display.KEY_SOFT2:return 32;//�\�t�g�Q�L�[;
            case Display.KEY_SOFT3:return 33;//�\�t�g�R�L�[;
            case Display.KEY_SOFT4:return 34;//�\�t�g�S�L�[;
            default :return 100;

        }
    }
    
    public void endscape(){
    	saveSetting();
        (StarApplication.getThisStarApplication()).terminate();//�A�v�����I������
    }

}