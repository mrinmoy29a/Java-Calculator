/*
Project Name: Ultimate Calculator
Submitted by:
    1.  Mahmudul Hasan Sumon
    2.  Azharul Islam
    3.  Mrinmoy Biswas Akash
    Dept. of Computer Science and Engineering, JKKNIU.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Calculator extends JFrame implements ActionListener {         /* JFrame is being extended here.
                                                                        We are turning Calculator class into a JFrame
                                                                            instead of creating a new one*/
    private JPanel[] row = new JPanel[6];
    private JButton[] buttons = new JButton[40];

    String[] buttonName = new String[]{"DEC", "BIN",
            "HEX", "OCT", "%", "\u221A", "\u2206"+"\u00B2", "+",
            "mod", "\u207F"+"\u221A", "\u2206"+"\u207F", "10"+"\u207F", "7", "8", "9", "-",
            "sin", "cos", "tan", "ANS", "4", "5", "6", "\u03A7",
            "sin-"+"\u00B9", "cos-"+"\u00B9", "tan-"+"\u00B9", "DEL",
            "1", "2", "3", "\u00F7", "log", "ln", "e", "\u03C0", "0", ".", "AC", "="
    };

	/*
	Dimensions are created to specify component size.
	*/
    private Dimension displayDimension = new Dimension(522, 60);
    private Dimension operationDisplayDimension = new Dimension(75, 60);
    private Dimension buttonDimension = new Dimension(75, 60);

    private JTextArea display = new JTextArea(1, 12);				// Main display of the calculator.
    private JTextArea operationDisplay = new JTextArea(1, 1);		// Operator display.

    Font font = new Font("Corbel", Font.PLAIN, 18);					// for buttons
    Font displayFont = new Font("Trebuchet MS", Font.BOLD, 34);	// for display, Euphemia

    FlowLayout displayLayout = new FlowLayout(FlowLayout.CENTER);		/* flow is a specific layout that automatically
																			places the components.*/
    FlowLayout buttonLayout = new FlowLayout(FlowLayout.CENTER, 1, 1);

    Double value1, answer=0.0;
    String DANS, DANS_PREV="";
    char operator;

    final double PI=Math.PI;									// Setting the value for pi.
    final double E=Math.E;									// Value of e.

    Boolean operationTaken=false;
    Boolean setDisplayEmpty=false;


    Calculator()
    {
        super("Ultimate Calculator");           // We had to call the original JFrame with super to set the title.
        setSize(615, 400);                      // Setting the size of the frame.
        setResizable(false);                    // The frame can not be resized.
        setDefaultCloseOperation(EXIT_ON_CLOSE);// The program exits if close is clicked, else runs in back.
        GridLayout grid = new GridLayout(6, 8);
        setLayout(grid);                        // Setting the layout to grid.

        for(int i=0;i<6;i++)                    // This loop creates 6 panels aka rows to contain the components.
        {
            row[i] = new JPanel();
            row[i].setBackground(new Color(4, 0, 8));
            if (i > 0)
                row[i].setLayout(buttonLayout);
            else
                row[i].setLayout(displayLayout);
        }

        for(int i=0;i<40;i++)                   // This loop set the button strings
        {
            buttons[i] = new JButton();
            buttons[i].setText(buttonName[i]);
            buttons[i].setFont(font);
            buttons[i].addActionListener(this);
            buttons[i].setPreferredSize(buttonDimension);
            buttons[i].setForeground(Color.WHITE);
            buttonColoring(i);
        }

        display.setFont(displayFont);                  // Set the display font
        display.setEditable(false);             // The text in display can't be edited
        display.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);     // Text appears right to left
        display.setPreferredSize(displayDimension);			// Fix the size of the display.
        display.setBackground(Color.BLACK);
        display.setForeground(new Color(131, 188, 35));    // Setting the display foreground color

        operationDisplay.setFont(displayFont);
        operationDisplay.setEditable(false);
        display.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        operationDisplay.setPreferredSize(operationDisplayDimension);
        operationDisplay.setForeground(new Color(52, 152, 219));
        operationDisplay.setBackground(new Color(0, 0, 0));

        /* Here we will be attaching the buttons to the rows*/

        row[0].add(operationDisplay);			// Same here
        row[0].add(display);					// The display is added to row[0], or the first panel.
        add(row[0]);							// The row[0] panel is added to the JFrame

        for(int i=1;i<6;i++)
        {
            for(int j=i*8-8; j<i*8; j++)
            {
                row[i].add(buttons[j]);			// A button is added to the panel.
            }
            add(row[i]);
        }

        setVisible(true);                       // Setting the frame visible. Won't show otherwise.
    }

    void buttonColoring(int i)
    {
        if(i>=0 && i<=3)
            buttons[i].setBackground(new Color(22, 160, 133));		// Creating a background color for buttons
        else if(i==19 || i==27)
            buttons[i].setBackground(new Color(192, 57, 43));
        else if(i>11 && (i+1)%8!=0 &&i%8>=4)
            buttons[i].setBackground(new Color(231, 76, 60));
        else if(i%8<4)
            buttons[i].setBackground(new Color(26, 188, 156));
        else
            buttons[i].setBackground(new Color(192, 57, 43));
    }

    void calculatorReset()							// AC all clear funtionality.
    {
        display.setText("");
        operationTaken = false;
        setDisplayEmpty = false;
        operationDisplay.setText("");
		DANS="";
    }

    void showAnswer()								// This method is used to display the answer or output
    {
        if(operationTaken && !display.getText().equals(""))
        {
            answer = Double.parseDouble(display.getText());
            switch(operator)
            {
                case '+':
                    DANS= Double.toString(answer+value1);
                    break;

                case '-':
                    DANS= Double.toString(value1-answer);
                    break;

                case '*':
                    DANS= Double.toString(answer*value1);
                    break;

                case '/':
                    DANS= Double.toString(value1/answer);
                    break;

                case '%':
                    DANS= Double.toString(value1%answer);
                    break;
                case 'r':
                    DANS= Double.toString(Math.pow(value1, 1/answer));
                    break;
                case '^':
                    DANS= Double.toString(Math.pow(value1, answer));
                    break;
                case 'p':
                    DANS= Double.toString( (value1 * answer)/100);
                    break;
            }
            value1=answer;
            operationTaken=false;
        }

        display.setText(DANS);
        DANS_PREV=DANS;									// previous answer funtionality.
        operationDisplay.setText("");
        setDisplayEmpty = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {		// e is the eventhandler specified by ActionEvent

        if(setDisplayEmpty)
        {
            display.setText("");
            setDisplayEmpty = false;
        }

        for(int i=0;i<40;i++)
        {
            if(e.getSource() == buttons[i] && i>11 &&i%8>=4 &&i%8<7)		/* getSource() gets source of the event.
																				In this case, a button.*/
            {
                if(i==38)
                    calculatorReset();

                else if(i==37)
                {
                    if(!display.getText().contains("."))					// gets the texts from the display
                        display.append(".");								// Only append a string to display, don't change it.
                }
                else
                    display.append(buttonName[i]);
            }

            else
            {
                if(e.getSource() == buttons[34])
                    display.setText(Double.toString(E));						// Sets the display output.

                else if(e.getSource() == buttons[35])
                    display.setText(Double.toString(PI));

                else if(e.getSource() == buttons[19])
                    display.setText(DANS_PREV);

                else if(e.getSource() == buttons[39])
                    showAnswer();

                else if(e.getSource() == buttons[i] && !display.getText().equals(""))
                    takeAction(i);
            }
        }

    }

    void takeAction(int i)
    {
        operationDisplay.setText(buttonName[i]);
        int a;

        switch(i)
        {
            case 0:                                                 // to decimal.
                answer = Double.parseDouble(display.getText());
                a = answer.intValue();
                DANS=Integer.toString(Integer.parseInt(String.valueOf(answer.intValue()), 2));
                break;

            case 1:                                                 // to binary.
                answer = Double.parseDouble(display.getText());
                a = answer.intValue();
                DANS=Integer.toBinaryString(a);
                break;

            case 2:                                                // to hexadecimal.
                answer = Double.parseDouble(display.getText());
                a = answer.intValue();
                DANS=Integer.toHexString(a);
                break;

            case 3:                                                // to octal.
                answer = Double.parseDouble(display.getText());
                a = answer.intValue();
                DANS=Integer.toOctalString(a);
                break;

            case 4:                                                // percent.
                if(!operationTaken) {
                    value1 = Double.parseDouble(display.getText());
                    display.setText("");
                    operator = 'p';
                    operationTaken=true;
                }
                break;

            case 5:                                                 // square root.
                answer = Double.parseDouble(display.getText());
                DANS=Double.toString(Math.sqrt(answer));
                break;

            case 6:                                                 // square.
                answer = Double.parseDouble(display.getText());
                DANS=Double.toString((long) Math.pow(answer, 2.0));
                break;

            case 7:                                                 // addition.
                if(!operationTaken) {
                    value1 = Double.parseDouble(display.getText());
                    display.setText("");
                    operator = '+';
                    operationTaken=true;
                }
                break;

            case 15:                                                 // subtraction.
                if(!operationTaken) {
                    value1 = Double.parseDouble(display.getText());
                    display.setText("");
                    operator = '-';
                    operationTaken=true;
                }
                break;

            case 23:                                                 // multiplication.
                if(!operationTaken) {
                    value1 = Double.parseDouble(display.getText());
                    display.setText("");
                    operator = '*';
                    operationTaken=true;
                }
                break;

            case 31:                                                 // division.
                if(!operationTaken) {
                    value1 = Double.parseDouble(display.getText());
                    display.setText("");
                    operator = '/';
                    operationTaken=true;
                }
                break;

            case 8:                                                 // mod.
                if(!operationTaken) {
                    value1 = Double.parseDouble(display.getText());
                    display.setText("");
                    operator = '%';
                    operationTaken=true;
                }
                break;

            case 9:                                                 // root.
                if(!operationTaken) {
                    value1 = Double.parseDouble(display.getText());
                    display.setText("");
                    operator = 'r';
                    operationTaken=true;
                }
                break;

            case 10:                                                 // power.
                if(!operationTaken) {
                    value1 = Double.parseDouble(display.getText());
                    display.setText("");
                    operator = '^';
                    operationTaken=true;
                }
                break;

            case 11:                                                 // ten to the power.
                answer = Double.parseDouble(display.getText());
                DANS=Double.toString((long) Math.pow(10, answer));
                break;

            case 16:                                                 // sin.
                answer = Double.parseDouble(display.getText());
                DANS=Double.toString(Math.sin(answer*PI/180.00));
                break;

            case 17:                                                 // cos.
                answer = Double.parseDouble(display.getText());
                DANS=Double.toString(Math.cos(answer*PI/180.00));
                break;

            case 18:                                                 // tan.
                answer = Double.parseDouble(display.getText());
                DANS=Double.toString(Math.tan(answer*PI/180.00));
                break;

            case 24:                                                 // asin.
                answer = Double.parseDouble(display.getText());
                DANS=Double.toString(180 * Math.asin(answer) / PI);
                break;

            case 25:                                                 // acos.
                answer = Double.parseDouble(display.getText());
                DANS=Double.toString(180 * Math.acos(answer)/PI);
                break;

            case 26:                                                 // atan.
                answer = Double.parseDouble(display.getText());
                DANS=Double.toString(180 * Math.atan(answer)/PI);
                break;

            case 27:                                                 // atan.
                String temp = display.getText();
                temp = temp.substring(0,temp.length()-1);
                display.setText(temp);
                break;

            case 32:                                                 // log.
                answer = Double.parseDouble(display.getText());
                DANS=Double.toString(Math.log10(answer));
                break;

            case 33:                                                 // ln.
                answer = Double.parseDouble(display.getText());
                DANS=Double.toString(Math.log(answer));
                break;

        }

    }




}


public class PCalculator {											// main function.

    public static void main(String args[])
    {
        Calculator cl = new Calculator();
    }
}
