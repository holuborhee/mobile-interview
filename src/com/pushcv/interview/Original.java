package com.pushcv.interview;




import com.codename1.io.*;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
//import com.codename1.media.Media;
//import com.codename1.media.MediaManager;
//import com.codename1.system.NativeLookup;
//import com.codename1.testing.DeviceRunner;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.UITimer;
import com.codename1.util.StringUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import com.codename1.ui.util.Resources;





public class Original {

    private Form current;
    
    private Form loginForm;
    private Form questionForm;
    private TextField emailField;
    private TextField voucherField;
    String path;
    Image error;
    Image information;
    Image loadingImage;
    Image doneImage;
    Image startUp;
    //Image icon;
    public void init(Object context) {
        try {
            r = Resources.open("/theme.res");
            error = r.getImage("error.jpg");
            information = r.getImage("information.png");
            //loadingImage = r.getImage("loadingtext.png");
            //doneImage = r.getImage("done.png");
            startUp = r.getImage("startup.png");
            //icon = r.getImage("icon.png");
            
            Resources theme = Resources.openLayered("/theme");
            
            UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
        } catch(IOException e){
            e.printStackTrace();
        }
        // Pro users - uncomment this code to get crash reports sent to you automatically
        /*Display.getInstance().addEdtErrorHandler(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                evt.consume();
                Log.p("Exception in AppName version " + Display.getInstance().getProperty("AppVersion", "Unknown"));
                Log.p("OS " + Display.getInstance().getPlatformName());
                Log.p("Error " + evt.getSource());
                Log.p("Current Form " + Display.getInstance().getCurrent().getName());
                Log.e((Throwable)evt.getSource());
                Log.sendLog();
            }
        });*/

        
        Display.getInstance().addEdtErrorHandler(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                evt.consume();                
                if(showDialog("OOPS!!!", "Something went wrong, this could mainly have been caused because this application is not granted permission to use your device's recorder, when prompted next, you have to allow. Click on OK to go back to the tips and start over again." + "\nError " + evt.getSource() + "\nPath: " + p, Dialog.TYPE_ERROR, "OK", null, error))
                {
                    if(question_text != null)
                        instructionForm();
                }
                Log.p("Exception in AppName version " + Display.getInstance().getProperty("AppVersion", "Unknown"));
                Log.p("OS " + Display.getInstance().getPlatformName());
                Log.p("Error " + evt.getSource());
                Log.p("Current Form " + Display.getInstance().getCurrent().getName());
                Log.e((Throwable)evt.getSource());
                Log.sendLog();
            
            }
        });
        
        
        
        
       /* NetworkManager.getInstance().addErrorListener(new ActionListener() {
     public void actionPerformed(ActionEvent evt) {
         //handle your error here consume the event
         evt.consume();
         showDialog("OOPS!!!", "Couldn't access the server, you need to be sure your internet connection is on, you need to be connected to the internet to use this application." + "\nError " + evt.getSource() + "\nPath: " + getTempFileName(), Dialog.TYPE_ERROR, "OK", null, error);
         
     }
});*/
    }
    
    int countTimer;
    Form splashscreen;
    public void start() {
        if(current != null){
            current.show();
            return;
        }
        splashscreen = new Form();
        BorderLayout lay = new BorderLayout();
        //lay.setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER);
        splashscreen.setLayout(lay);
        Label startUpImage = new Label("");
        startUpImage.setUIID("imagelabel");
        splashscreen.addComponent(BorderLayout.CENTER,startUpImage);
        splashscreen.show();
        countTimer = 3;
        UITimer timeSplash = new UITimer(new Runnable() {

            public void run() {
              countTimer--;
              if(countTimer == 0)
              {
                  logInForm();
              }
            }
        });
        
        timeSplash.schedule(1000, true, splashscreen);
        
        
        
    }
    
    
    

    public void stop() {
        current = Display.getInstance().getCurrent();
    }
    
    
    
    
    
    public void destroy() {
    }
    
    
    
        
         
        
        
   
    
    
    int i;
    Label alertLabel;
    UITimer timing;
    Button recordButton;
    TableLayout table;
    TableLayout.Constraint constraint;
   // boolean recording = false;
    public void questionForm()
    {
        i = 15;
        questionForm = new Form();
        questionForm.setUIID("formtip");
        questionForm.setScrollable(false);
        
        table = new TableLayout(3,1);
        Container questionConatiner = new Container(table);
        questionConatiner.setUIID("questioncontainer");
        
        
        TextArea questionArea = new TextArea();
        questionArea.setUIID("questionlabel");
        questionArea.setText(question_text);
        recordButton = new Button("START RECORDING");
        recordButton.setUIID("redbutton");
        questionArea.setEditable(false);
        
        
        
        alertLabel = new Label("00:15");
        alertLabel.setUIID("centerlabel");
        timing  = new UITimer(new Runnable() {

            public void run() {
                i--;
                if(i>=10)
                alertLabel.setText("00:" + i );
                else
                   alertLabel.setText("00:0" + i ); 
                if(i == 0)
                {
                   startRecord();
                   
                }
            }
        });
        Container empty;
        
        timing.schedule(1000, true, questionForm);
        
        
        
        constraint = table.createConstraint();
        constraint.setHeightPercentage(40);
        constraint.setWidthPercentage(100);
        constraint.setVerticalAlign(Component.CENTER);
        questionConatiner.addComponent(constraint, questionArea);
        
        
        table = new TableLayout(3,1);
        Container timeContainer = new Container(table);
        empty = new Container();
        constraint = table.createConstraint();
        constraint.setHeightPercentage(15);
        constraint.setWidthPercentage(100);
        timeContainer.addComponent(constraint, empty);
        constraint = table.createConstraint();
        constraint.setHeightPercentage(70);
        constraint.setWidthPercentage(100);
        timeContainer.addComponent(constraint, alertLabel);
        empty = new Container();
        constraint = table.createConstraint();
        constraint.setHeightPercentage(15);
        constraint.setWidthPercentage(100);
        timeContainer.addComponent(constraint, empty);
        
        
        
        
        
        
        table = new TableLayout(3,1);
        questionForm.setLayout(table);
        
        
        constraint = table.createConstraint();
        constraint.setHeightPercentage(60);
        constraint.setWidthPercentage(100);
        questionForm.addComponent(constraint, questionConatiner);
        
        
        constraint = table.createConstraint();
        constraint.setHeightPercentage(25);
        constraint.setWidthPercentage(100);
        questionForm.addComponent(constraint, timeContainer);
        
        
        constraint = table.createConstraint();
        constraint.setHeightPercentage(15);
        constraint.setWidthPercentage(100);
        questionForm.addComponent(constraint, recordButton);
        
        recordButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
            /*    if(recording)
                    stopRecord(false);
               else*/
                  startRecord();
          
                    


                
            }
        });
        
       questionForm.show(); 
        
        
        
    }
    
    
    
    
    
    private com.codename1.io.MultipartRequest request;
    public void stopRecord(boolean timeElapsed)
    {
        answertiming.cancel();
        
        //for javaSE
       /* if(audiorecord!=null && audiorecord.isSupported())
        {
            
            audiorecord.stopRecord(timeElapsed);
            recording = false;
        }*/
        
      //for mobile
        media.pause();
        media.cleanup(); 
        
        sendToNetwork(timeElapsed);
    }
    Button logInButton;
    public void logInForm()
    {
        loginForm = new Form();
        
        
        loginForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        loginForm.setScrollableY(true);
        //TableLayout.Constraint constraint;
        
        
        emailField = new TextField();
        voucherField = new TextField();
        /*emailField.setHeight(50);
        voucherField.setHeight(50);*/
        logInButton = new Button("LOGIN");
        logInButton.setUIID("padedbutton");
        emailField.setConstraint(TextField.EMAILADDR);
        voucherField.setConstraint(TextField.ANY);
        /*emailField.setHint("Email Address");
        voucherField.setHint("Activated Voucher");*/
        Button lostUsername = new Button("Activate Voucher");
        lostUsername.setUIID("Link");
        lostUsername.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                Display.getInstance().execute("https://www.pushcv.com/mockactivate");
                

            }
        });
        Label loginLabel = new Label("Login");
        loginLabel.setUIID("login");
        //Label faq = new Label("FAQ");
        Button faq = new Button("FAQ");
        faq.setUIID("Link");
        
        faq.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                Display.getInstance().execute("https://www.pushcv.com/mockfaq");
            }
        });
        
        
        Container north = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container south = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        BorderLayout borderlayout = new BorderLayout();
        Container newContainer = new Container(borderlayout);
        Container fillspace = new Container();
        fillspace.setPreferredSize(new Dimension(100, 100));
        
        north.setUIID("northcontainer");
        north.setScrollable(false);
        
    
        
        Label icon = new Label("");
        icon.setPreferredSize(new Dimension(200, 100));
        icon.setUIID("imagelabel");
        Label label1 = new Label("Enter Interview Voucher Code");
        Label label2 = new Label("Email Address");
        north.addComponent(icon);
        north.addComponent(label1);
        north.addComponent(voucherField);
        north.addComponent(label2);
        north.addComponent(emailField);
        
        south.addComponent(logInButton);
        south.addComponent(lostUsername);
        south.addComponent(faq);
        south.setScrollable(false);
        newContainer.addComponent(BorderLayout.CENTER, fillspace);
        newContainer.addComponent(BorderLayout.SOUTH, south);
        loginForm.addComponent(north);
        loginForm.addComponent(newContainer);
        //if (Display.getInstance().getPlatformName().equals("rim")) {
            loginForm.addCommand(new Command("Exit Application"){

                @Override
                public void actionPerformed(ActionEvent evt) {
                    if(showDialog("EXIT", "Do you want to Close App?", Dialog.TYPE_CONFIRMATION, "YES", "NO", information))
                Display.getInstance().exitApplication();
                }
                
            });
            
       // }
        loginForm.show();
        logInButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                
                if(!emailField.getText().equals("") && !voucherField.getText().equals(""))
                {
                    logInButton.setText("LOADING...");
                    logInButton.setEnabled(false);
                    validateLogIN(emailField.getText(), voucherField.getText());
                    
                    
                }
                else
                {
                    showDialog("EMPTY FIELDS", "Your email or voucher field is currently empty, you cannot continue when their is no value in them", Dialog.TYPE_ERROR, "OK", null, error);
                }
                                    
            }
           
        });
    }
    
    
    
    private void howToUse()
    {
        Form instructionForm = new Form();
        //instructionForm.setUIID("formtip");
        String howTOUse [] = {"You seeing this page means you have successfully logged In","The next  page after this offers you some basic tips for an interview","The Question page follows that.","On the question page, you have only fifteen seconds tostare at a question.","You can click answer question before the time elapse","If you click the button it starts recording, if also the time elapse itself it does the same","You have 30 seconds to answer one question"};
        TextArea instructionlabels[] = new TextArea[howTOUse.length];
        TableLayout table1 = new TableLayout(3, 1);
        TableLayout.Constraint constraint;
        instructionForm.setLayout(table1);
        instructionForm.setScrollable(false);
        
        
        Container tipContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        tipContainer.setUIID("tip");
        tipContainer.setScrollableY(true);
        for(int ij = 0; ij<instructionlabels.length; ij++)
        {
            instructionlabels[ij] = new TextArea(howTOUse[ij]);
            if(ij==0 || ij%2==0)
               instructionlabels[ij].setUIID("evenhint");
            else
               instructionlabels[ij].setUIID("oddhint"); 
            instructionlabels[ij].setEditable(false);
            tipContainer.addComponent(instructionlabels[ij]);
        }
        
        Button startButton = new Button("NEXT");
        startButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                instructionForm();
            }
        });
        
        Container top = new Container(new BorderLayout());
        top.setUIID("lost");
        Label ti = new Label("HOW TO USE");
        ti.setUIID("centerlabel");
        top.addComponent(BorderLayout.SOUTH, ti);
        
        constraint = table1.createConstraint();
        constraint.setHeightPercentage(15);
        constraint.setWidthPercentage(100);
        //constraint.setVerticalAlign(Component.TOP);
        instructionForm.addComponent(constraint,top);
        
        constraint = table1.createConstraint();
        constraint.setHeightPercentage(70);
        constraint.setWidthPercentage(100);
        instructionForm.addComponent(constraint,tipContainer);
        
        constraint = table1.createConstraint();
        constraint.setHeightPercentage(15);
        constraint.setWidthPercentage(100);
        instructionForm.addComponent(constraint,startButton);
        //if (Display.getInstance().getPlatformName().equals("rim")) {
            instructionForm.addCommand(new Command("Exit Application"){

                @Override
                public void actionPerformed(ActionEvent evt) {
                    if(showDialog("EXIT", "Do you want to Close App?", Dialog.TYPE_CONFIRMATION, "YES", "NO", information))
                Display.getInstance().exitApplication();
                }
                
            });
            
       // }
        instructionForm.show();
    }
    
    private void instructionForm()
    {
        Form instructionForm = new Form();
        //instructionForm.setUIID("formtip");
        TextArea instructionlabels[] = new TextArea[tips.length];
        TableLayout table1 = new TableLayout(3, 1);
        TableLayout.Constraint constraint;
        instructionForm.setLayout(table1);
        instructionForm.setScrollable(false);
        
        
        Container tipContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        tipContainer.setUIID("tip");
        tipContainer.setScrollableY(true);
        for(int ij = 0; ij<instructionlabels.length; ij++)
        {
            instructionlabels[ij] = new TextArea(tips[ij]);
            if(ij==0 || ij%2==0)
               instructionlabels[ij].setUIID("evenhint");
            else
               instructionlabels[ij].setUIID("oddhint");
            instructionlabels[ij].setEditable(false);
            tipContainer.addComponent(instructionlabels[ij]);
        }
        
        Button startButton = new Button("START INTERVIEW");
        startButton.setUIID("bluebutton");
        startButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                questionForm();
            }
        });
        
        Container top = new Container(new BorderLayout());
        top.setUIID("lost");
        Label ti = new Label("TIPS");
        ti.setUIID("centerlabel");
        top.addComponent(BorderLayout.SOUTH, ti);
        
        constraint = table1.createConstraint();
        constraint.setHeightPercentage(15);
        constraint.setWidthPercentage(100);
        //constraint.setVerticalAlign(Component.TOP);
        instructionForm.addComponent(constraint,top);
        
        constraint = table1.createConstraint();
        constraint.setHeightPercentage(70);
        constraint.setWidthPercentage(100);
        instructionForm.addComponent(constraint,tipContainer);
        
        constraint = table1.createConstraint();
        constraint.setHeightPercentage(15);
        constraint.setWidthPercentage(100);
        instructionForm.addComponent(constraint,startButton);
        //if (Display.getInstance().getPlatformName().equals("rim")) {
            instructionForm.addCommand(new Command("Exit Application"){

                @Override
                public void actionPerformed(ActionEvent evt) {
                    if(showDialog("EXIT", "Do you want to Close App?", Dialog.TYPE_CONFIRMATION, "YES", "NO", information))
                Display.getInstance().exitApplication();
                }
                
            });
            
       // }
        instructionForm.show();
        
        
    }
    ConnectionRequest myrequest;
    Hashtable response;
    private String mock_session;
    private String mock_email;
    private String question_text;
    private String question_id;
    private String [] tips;
    private int num_tips;
    public void validateLogIN(String email, String voucher)
    {
        //Connects to the login page and collect JSON and process it.
        myrequest = new ConnectionRequest(){
            
            @Override
            protected void handleErrorResponseCode(int code, String message) {
                    
            }


            @Override
            protected void handleException(Exception err) {
                
                    
                    if(showDialog("OOPS!!!", "Couldn't access the server, you need to be sure your internet connection is on, you need to be connected to the internet to use this application." + "\nError: " + err.toString(), Dialog.TYPE_ERROR, "OK", null, error))
                    {
                        logInButton.setText("LOGIN");
                        logInButton.setEnabled(true);
                    }
                    

            }

            @Override
            protected void readResponse(InputStream input) throws IOException {
                JSONParser parser = new JSONParser();
                response =  parser.parse(new InputStreamReader(input));
                    if(response.get("m_session").toString().equals("0") || response.get("m_session_email").toString().equals("0"))
                    {
                        showDialog("INVALID CREDENTIALS", "Invalid email and voucher combination, be sure you have activated your voucher and try again.", Dialog.TYPE_ERROR, "OK", null, error);
                        logInButton.setText("LOGIN");
                        logInButton.setEnabled(true);
                    }
                    else
                    {
                        mock_session = response.get("m_session").toString();
                        mock_email = response.get("m_session_email").toString();
                        question_text = response.get("thetext").toString();
                        question_id = response.get("therand").toString();
                        
                        String tipnummm = response.get("thetipnum").toString();
                        
                       
                        int in = tipnummm.indexOf(".");
                        if(in != -1)
                        {
                            tipnummm = tipnummm.substring(0, in);
                        }
                        tips = new String[Integer.parseInt(tipnummm)];
                        
                        String thetips= response.get("thetip").toString();                                     //\u2019
                        
                        
                        
                        
                        String tiips[] = isplit(thetips,"\",\"");
                        
                        
                        
                        int location;
                        for(int i = 0; i < tiips.length; i++)
                        {
                            location = tiips[i].indexOf(":");
                            
                            tiips[i] = tiips[i].substring(location+2);
                            tiips[i] = StringUtil.replaceAll(tiips[i], "\\u2019", "'");
                            if(i==tiips.length-1)
                            {
                               tiips[i] =  StringUtil.replaceAll(tiips[i], "\"}", "");
                            }
                            
                            tips[i] = tiips[i];
                            
                        }
                        
                        
                        howToUse();
                        
                    }
                
            }
        
        
        
        
        };
        
        String deviceType = Display.getInstance().getProperty("OS", "");
                                        myrequest.setUrl("https://www.pushcv.com/pmi_auth");
                                        myrequest.setPost(true);
                                        myrequest.addArgument("email", email);
                                        myrequest.addArgument("code", voucher);
                                        myrequest.addArgument("action", deviceType);
                                        myrequest.setPriority(ConnectionRequest.PRIORITY_CRITICAL);
                                        NetworkManager.getInstance().addToQueue(myrequest);
                                        
                                        
                                       
        
    }
    
    
    
    public String[] isplit(String tosplit, String pattern)
    {
        ArrayList<String> li = new ArrayList<String>();
        int len = pattern.length();
        boolean con = true;
        int ind;
        while(con)
        {
           ind = tosplit.indexOf(pattern);
           if(ind>0)
           {
               li.add(tosplit.substring(0, ind));
               
               tosplit = tosplit.substring(ind+len);
               
           }
           else
           {
              li.add(tosplit);
              con = false;
           }
        }
        
        
        String ret[] = new String[li.size()];
        return li.toArray(ret);   
        
        
        
    }
    
    UITimer answertiming;
    Label timeLabel;
    Form record;
    public void startRecord()
    {    
        
        
       i = 30;
       timing.cancel();
        
        // the recording form for mobile starts here, to be disabled for javaSE
        
        
        record = new Form();
        record.setUIID("formtip");
        record.setScrollable(false);
       // TableLayout table;
       // TableLayout.Constraint constraint;
        table = new TableLayout(3,1);
        Container questionConatiner = new Container(table);
        questionConatiner.setUIID("questioncontainer");
        
        Button doneButton = new Button("DONE");
        TextArea questionLabel = new TextArea();
        questionLabel.setUIID("questionlabel");
        questionLabel.setText("Question: " + question_text);
        questionLabel.setEditable(false);
        
        
        
        timeLabel = new Label("00:30");
        timeLabel.setUIID("centerlabel");
        
        doneButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                stopRecord(false);
                
            }
        });
        
        Container empty;
        
        timing.schedule(1000, true, questionForm);
        
        
        
        constraint = table.createConstraint();
        constraint.setHeightPercentage(40);
        constraint.setWidthPercentage(100);
        constraint.setVerticalAlign(Component.CENTER);
        questionConatiner.addComponent(constraint, questionLabel);
        
        
        table = new TableLayout(3,1);
        Container timeContainer = new Container(table);
        empty = new Container();
        constraint = table.createConstraint();
        constraint.setHeightPercentage(15);
        constraint.setWidthPercentage(100);
        timeContainer.addComponent(constraint, empty);
        constraint = table.createConstraint();
        constraint.setHeightPercentage(70);
        constraint.setWidthPercentage(100);
        timeContainer.addComponent(constraint, timeLabel);
        empty = new Container();
        constraint = table.createConstraint();
        constraint.setHeightPercentage(15);
        constraint.setWidthPercentage(100);
        timeContainer.addComponent(constraint, empty);
        
        
        
        
        
        
        table = new TableLayout(3,1);
        record.setLayout(table);
        
        
        constraint = table.createConstraint();
        constraint.setHeightPercentage(60);
        constraint.setWidthPercentage(100);
        record.addComponent(constraint, questionConatiner);
        
        
        constraint = table.createConstraint();
        constraint.setHeightPercentage(25);
        constraint.setWidthPercentage(100);
        record.addComponent(constraint, timeContainer);
        
        
        constraint = table.createConstraint();
        constraint.setHeightPercentage(15);
        constraint.setWidthPercentage(100);
        record.addComponent(constraint, doneButton);
           //ends here disable for javaSE
        
        answertiming = new UITimer(new Runnable() {

            public void run() {
               
               if(i>=10)
                   timeLabel.setText("00:" + i );     //alertLabel.setText("00:" + i ); for mobile
                else
                   timeLabel.setText("00:0" + i );   //alertLabel.setText("00:0" + i );  for mobile
               if(i == 0)
               {
                   stopRecord(true);
               }
               i--;
            }
        });
        
        
        //for mobile
        record.show();
        
                                                  
        
        
        
        
       /* questionForm.revalidate();                                //  for java SE line1
        recordButton.setUIID("Button");                            //  for java SE line2
        recordButton.setText("DONE");
        
        questionForm.animateHierarchyAndWait(300); */                  // for java SE line3
        answertiming.schedule(1000, true, record);   //for mobile
        //answertiming.schedule(1000, true, questionForm);  //javaSE
        doRecord();
        
        
    }
    
    
    
    Media media; //for mobile
    String p;
    String filename;
   // AudioRecordingInterface audiorecord;//for javaSE
    private void doRecord()
    {
        
        // initites devices microphone and do the recording until it is stopped.
        
        
        // for java SE
        
      /*  audiorecord = (AudioRecordingInterface) NativeLookup.create(AudioRecordingInterface.class); 
        if(audiorecord!=null && audiorecord.isSupported())
        {
            p= audiorecord.getTempFileName();
            audiorecord.start();
            recording = true;
        }*/
        
        //for mobile
       p = getTempFileName();
       try{
            
            media = MediaManager.createMediaRecorder(p,MediaManager.getMediaRecorderingMimeType());
            media.play();
      }catch (IOException ex) {
           ex.printStackTrace();
       } 
    }

    private String getTempFileName() {
        //String[] roots = FileSystemStorage.getInstance().getRoots();
        // iOS doesn't have an SD card
        String root = FileSystemStorage.getInstance().getAppHomePath();
        filename = "audioSample" + System.currentTimeMillis();
        return root + filename;
    }


    UITimer timehint;
    Button nextQuestion;
    TextArea lab;
    Form waitForm;
    Button closeApp;
    Container con;
    Label loading;
    Resources r;
    private void sendToNetwork(boolean timeElapsed) {
        waitForm = new Form();
        BorderLayout bl = new BorderLayout();
        closeApp = new Button("Close");
        closeApp.setUIID("close");
        closeApp.setEnabled(false);
        con = new Container(new BorderLayout());
        con.addComponent(BorderLayout.EAST,closeApp);
        closeApp.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                if(showDialog("EXIT", "You are about to end this Interview when you have not finished answering the questions \nThe system has saved your current session and it will start with the next question when you resume.", Dialog.TYPE_CONFIRMATION, "YES", "NO", information))
                Display.getInstance().exitApplication();

            }
        });
        waitForm.setLayout(bl);
         lab = new TextArea();
         lab.setEditable(false);
        if(timeElapsed)
        {
           lab.setText("Your time is UP, System has saved your answer and is preparing next question"); 
        }
        else
            lab.setText("Your have successfully answered that question, System has saved your answer and is preparing next question");
        i = tips.length-1;
        timehint = new UITimer(new Runnable() {

            public void run() {
                if(i<0)
                {
                   i = tips.length -1;
                   lab.setText("Thanks for your patience, System is still processing next question... Please wait and continue reading the tips");
                }
                else
                {
                
                lab.setText(tips[i]);
                i--;
                }
            }
        });
        
        
        nextQuestion = new Button("NEXT QUESTION");
        nextQuestion.setUIID("padedbutton");
        nextQuestion.setEnabled(false);
        
        nextQuestion.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                if(question_text.equals("FINISH"))
                    Display.getInstance().exitApplication();
                else
                questionForm();
            }
        });
        
        
        
            
            loading = new Label("");
            loading.setUIID("loadinglabel");
            loading.setEnabled(false);
            TableLayout cent = new TableLayout(2, 1);
        TableLayout.Constraint cons;
        
        Container center = new Container(cent);
        
        cons = cent.createConstraint();
        cons.setHeightPercentage(70);
        cons.setWidthPercentage(100);
        center.addComponent(cons, loading);
        
        
        cons = cent.createConstraint();
        cons.setHeightPercentage(30);
        cons.setWidthPercentage(100);
        center.addComponent(cons, lab);
        
        waitForm.addComponent(BorderLayout.NORTH,con);
        
        waitForm.addComponent(BorderLayout.CENTER,center);
        
        
        waitForm.addComponent(BorderLayout.SOUTH,nextQuestion);
    
        waitForm.show();
        
           
            try {
            
            
            
        //upload here
        request = new com.codename1.io.MultipartRequest(){
            
            
            
            
            @Override
            protected void handleErrorResponseCode(int code, String message) {
                    
            }


            @Override
            protected void handleException(Exception err) {
                
                    
                    if(showDialog("OOPS!!!", "Couldn't access the server, you need to be sure your internet connection is on, you need to be connected to the internet to use this application." + "\nError " + err.toString() , Dialog.TYPE_ERROR, "PROCEED >>", null, error))
                    {
                        instructionForm();
                    }
                    

            }
            
                @Override
            protected void readResponse(InputStream input) throws IOException {
                JSONParser parser = new JSONParser();
                response =  parser.parse(new InputStreamReader(input));
                
                    

                if(response.get("thetext") != null)
                {                
                question_text = response.get("thetext").toString();
                }
                else
                question_text = "FINISH";
                //waitForm.revalidate();
                if(question_text.equals("FINISH"))
                {
                    timehint.cancel();
                    lab.setText("Thank You For Performing the interview, \r\nYou can now log out of the system");
                    nextQuestion.setText("DONE");
                    nextQuestion.setEnabled(true);
                    FileSystemStorage.getInstance().deleteRetry(p, 3);
                }
                else
                {
                    
                    question_id = response.get("therand").toString();
                    String tipnummm = response.get("thetipnum").toString();    
                       
                        int in = tipnummm.indexOf(".");
                        if(in != -1)
                        {
                            tipnummm = tipnummm.substring(0, in);
                        }
                        
                        tips = new String[Integer.parseInt(tipnummm)];
                        
                        String thetips= response.get("thetip").toString();                                     //\u2019
                        
                        
                        
                        
                        String tiips[] = isplit(thetips,"\",\"");
                        
                        
                        
                        int location;
                        for(int i = 0; i < tiips.length; i++)
                        {
                            location = tiips[i].indexOf(":");
                            
                            tiips[i] = tiips[i].substring(location+2);
                            tiips[i] = StringUtil.replaceAll(tiips[i], "\\u2019", "'");
                            if(i==tiips.length-1)
                            {
                               tiips[i] =  StringUtil.replaceAll(tiips[i], "\"}", "");
                            }
                            
                            tips[i] = tiips[i];
                            
                        }
                        
                        timehint.cancel();
                        
                        lab.setText("Your next Question is Ready Click the button to answer it");
                        
                        nextQuestion.setEnabled(true);
                        FileSystemStorage.getInstance().deleteRetry(p, 3);
                        closeApp.setEnabled(true);
                }
                
                
            
            loading.setEnabled(true);
                nextQuestion.setEnabled(true);
                //waitForm.animateHierarchyAndWait(1000);
                
                
                        

                }
            
        };
        request.setUrl("https://www.pushcv.com/pmiuploadapp");
        request.setPost(true);
        request.addData("data", p,MediaManager.getMediaRecorderingMimeType()); //for mobile
        //request.addData("data", p,"audio/wav");   //for javaSE
        request.addArgument("int_id",mock_session);
        request.addArgument("quesID",question_id);
        request.addArgument("mock_session",mock_session);
        request.addArgument("mock_email",mock_email);
        //request.addArgument("fname",filename);
        

        request.setPriority(ConnectionRequest.PRIORITY_CRITICAL);
        NetworkManager.getInstance().addToQueue(request);
        timehint.schedule(6000, true, waitForm);
        
        
        
        } catch (IOException ex) {
            
        }
            
            
        

                
            
        
        
                     
                
        
    }
    
    
    boolean bool;
    private boolean showDialog(String title, String message, int messageType, String okMessage, String cancelMessage, Image icon)
    {
        bool = true;
        TableLayout tabledialog = new TableLayout(3, 1);
        TableLayout.Constraint constraintdialog;
        Container dialocon = new Container(tabledialog);
        Container buttonContainer;
        Container titleContainer = new Container(new FlowLayout(Component.CENTER));
        if(title == null)
            title = "INFORMATION";
        Label labTitle = new Label(title);
        labTitle.setUIID("centerlabel");
        
        if(icon != null)
        {
            Label labImage = new Label(icon);
            labImage.setUIID("centerlabel");
            titleContainer.addComponent(labImage);
        }
        
        titleContainer.addComponent(labTitle);
        if(okMessage == null)
            okMessage = "OK";
        Button ok = new Button(okMessage);
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                Dialog dlg = (Dialog)Display.getInstance().getCurrent();
                dlg.dispose();
                bool = true;
            }
        });
        
        TextArea infoArea = new TextArea(message);
        //infoArea.setUIID("questionlabel");
        infoArea.setEditable(false);
        
        if(cancelMessage == null)
        {
        buttonContainer = new Container(new BorderLayout());
        buttonContainer.addComponent(BorderLayout.CENTER,ok);
        }
        
        else
        {
            TableLayout buttontable = new TableLayout(1, 2);
            buttonContainer = new Container(buttontable);
            TableLayout.Constraint contraintbutton;
            
            
            contraintbutton = buttontable.createConstraint();
            contraintbutton.setHeightPercentage(100);
            contraintbutton.setWidthPercentage(50);
            buttonContainer.addComponent(contraintbutton,ok);
        Button can = new Button(cancelMessage);
        can.setUIID("redbutton");
        
        contraintbutton = buttontable.createConstraint();
        contraintbutton.setHeightPercentage(100);
            contraintbutton.setWidthPercentage(50);
            buttonContainer.addComponent(contraintbutton,can);
        
        can.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    Dialog dlg = (Dialog)Display.getInstance().getCurrent();
                    dlg.dispose();
                    bool = false;
                }
            });
        }
        constraintdialog = tabledialog.createConstraint();
        constraintdialog.setVerticalAlign(Component.TOP);
        constraintdialog.setHeightPercentage(10);
        constraintdialog.setWidthPercentage(100);
        dialocon.addComponent(constraintdialog, titleContainer);
        
        
        constraintdialog = tabledialog.createConstraint();
        constraintdialog.setHeightPercentage(75);
        constraintdialog.setWidthPercentage(100);
        dialocon.addComponent(constraintdialog, infoArea);
        
        
        constraintdialog = tabledialog.createConstraint();
        constraintdialog.setHeightPercentage(15);
        constraintdialog.setWidthPercentage(100);
        dialocon.addComponent(constraintdialog, buttonContainer);
        
        
        Dialog.show(null, dialocon, null, messageType, null);
        
            return bool;
        
    }

}