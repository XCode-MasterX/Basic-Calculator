// IMPORTS START
import java.awt.Shape;
import java.awt.Graphics;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
// IMPORTS END

// THE MAIN CLASS.
public class CalculatorGUI extends JFrame
{
    private HistoryVisualizer history;
    private String prevText = "";
    private int i_FontSize = 40;
    private final int WIDTH = 345, HEIGHT = 600;
    private final Calculator calc = new Calculator();
    private final String NUMBER_PARTS = "0123456789.";
    ArrayList<String> lastPress = new ArrayList<String>();

    public static void main(String args[])
    {
        new CalculatorGUI();
        System.gc();
    }

    private CalculatorGUI()
    {
        char symbols[] = {'C', 'B', '|', '/', '7', '8', '9', '*', '4', '5', '6', '-', '1', '2', '3', '+', '0', '.', '=', '^', 'H'};
        int labelWidth[] = {70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 150, 70, 70, 70, 70};
        int labelHeight[] = {70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70};
        int labelX[] = {10, 90, 170, 250, 10, 90, 170, 250, 10, 90, 170, 250, 10, 90, 170, 250, 10, 170, 250, 10, 90, 170, 250, 10};
        int labelY[] ={80, 80, 80, 80, 160, 160, 160, 160, 240, 240, 240, 240, 320, 320, 320, 320, 400, 400, 400, 480, 480, 480, 480};

        ImageIcon img[] = loadImages(new String[]{"C:\\Users\\HP\\Downloads\\Capture (2).png", "C:\\Users\\HP\\Downloads\\Capture (2).png",
        "C:\\Users\\HP\\Downloads\\Capture (2).png", "C:\\Users\\HP\\Downloads\\Capture (2).png",
        "C:\\Users\\HP\\Downloads\\Capture (2).png", "C:\\Users\\HP\\Downloads\\Capture (2).png",
        "C:\\Users\\HP\\Downloads\\Capture (2).png", "C:\\Users\\HP\\Downloads\\Capture (2).png",
        "C:\\Users\\HP\\Downloads\\Capture (2).png", "C:\\Users\\HP\\Downloads\\Capture (2).png",
        "C:\\Users\\HP\\Downloads\\Capture (2).png", "C:\\Users\\HP\\Downloads\\Capture (2).png",
        "C:\\Users\\HP\\Downloads\\Capture (2).png", "C:\\Users\\HP\\Downloads\\Capture (2).png",
        "C:\\Users\\HP\\Downloads\\Capture (2).png", "C:\\Users\\HP\\Downloads\\Capture (2).png",
        "C:\\Users\\HP\\Downloads\\Capture (2).png", "C:\\Users\\HP\\Downloads\\Capture (2).png",
        "C:\\Users\\HP\\Downloads\\Capture (2).png"});
        
        JTextField textField = new RJTextField(20);
        textField.setOpaque(false);
        textField.setEditable(false);
        textField.setBounds(5, 5, 320, 70);
        textField.setFont(new Font("Arial", Font.BOLD, 40));
        textField.setForeground(new java.awt.Color(0xffffff));

        textField.getDocument().addDocumentListener(new DocumentListener(){
            public void checkFont()
            {
                i_FontSize = Math.max(Math.min((int) (40 * (11.0f / textField.getText().length())), 40), 20);
                textField.setFont(new Font("Arial", Font.ROMAN_BASELINE, i_FontSize));
            }
            public void changedUpdate(DocumentEvent e) {
                checkFont();
            }
              public void removeUpdate(DocumentEvent e) {
                checkFont();
            }
              public void insertUpdate(DocumentEvent e) {
                checkFont();
            }
        });

        this.setContentPane(new JLabel("background"));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(0, 0, WIDTH, HEIGHT);
        this.setResizable(false);
        this.setBackground(new java.awt.Color(10, 10, 10));
        this.add(textField);

        for(int i = 0; i < symbols.length; i++) {
            JLabel label = new JLabel(img[0]);

            if(symbols[i] == '=')
            {
                label.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e)
                    {
                        String equation = prevText;
                        if(prevText.length() > 0 && !Character.isDigit(prevText.charAt(prevText.length() - 1)))
                            prevText = calc.calculate(prevText + " + 0");
                        else if(prevText.length() > 0)
                            prevText = calc.calculate(prevText);
                        history.addHistory(equation, prevText);
                        textField.setText(prevText + " = ?");
                    }

                    public void mouseEntered(MouseEvent e)
                    {
                        prevText = textField.getText();
                        if(prevText.length() > 0)
                            textField.setText(prevText + " = ?");
                    }

                    public void mouseExited(MouseEvent e)
                    {
                        textField.setText(textField.getText().replace(" = ?", ""));
                    }
                });
            }
            else if(symbols[i] == '.')
            {
                comp.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e)
                    {
                        String text = textField.getText();
                        if(text.length() == 0)
                            textField.setText("0.");
                        else
                            if(NUMBER_PARTS.contains(text.charAt(text.length() - 1) + ""))
                            {textField.setText(textField.getText() + '.'); lastPress.add(".");}
                            else
                            {textField.setText(textField.getText() + "0."); lastPress.add("0.");}
                    }
                });
            }
            else if("+-/*|^".contains(symbols[i] + ""))
            {
                final char c = symbols[i];
                comp.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e)
                    {
                        String text = textField.getText();
                        if(text.length() > 0)
                            if(NUMBER_PARTS.contains(text.charAt(text.length() - 1) + ""))
                            {
                                textField.setText(text +  " " + c + " ");
                                lastPress.add(" " + c + " ");
                            }
                    }
                });
            }
            else if(symbols[i] == 'C')
            {
                comp.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) { textField.setText(""); }
                });
            }
            else if(symbols[i] == 'B')
            {
                comp.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        String text = textField.getText();
                        if(lastPress.size() > 0){
                            int size = lastPress.size() - 1;
                            System.out.println(lastPress.get(size));
                            textField.setText(text.substring(0, text.length() - lastPress.get(size).length())); 
                            lastPress.remove(size);
                        }
                    }
                });
            }
            else if(symbols[i] == 'H')
            {
                label.addMouseListener(new MouseAdapter(){
                    public void mouseClicked()
                    {
                        history.makeVisible();
                    }
                });
            }
            else
                label.addMouseListener(new MouseListen(textField, symbols[i], this));

            label.setBounds(labelX[i], labelY[i], labelWidth[i], labelHeight[i]);
            this.add(label);
        }

        history = new HistoryVisualizer(this);
        this.setVisible(true);
    }

    private ImageIcon[] loadImages(String imgFiles[])
    {
        ImageIcon img[] = new ImageIcon[imgFiles.length];
        try{
            for(int i = 0; i < imgFiles.length; i++)
                img[i] = new ImageIcon(ImageIO.read(new File(imgFiles[i])));
            return img;
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}

// THE BASIS OF THIS ENTIRE THING.
class Calculator {
    private final String NUMBERS = "0123456789."; // The characters that can be used for writing a number.

    // THE MOST IMPORTANT FUNCTION IN THE ENTIRE PROGRAM.
    public String calculate(String s_Expression)
    {
        String s_Tokens[] = s_Expression.split(" "); // Getting the different operands and operators
        char symbols[] = {'/', '*', '+', '-', '|'}; // The symbols that are allowed in the expression.

        for(char i : symbols)
        {
            while(s_Expression.indexOf(i) > -1) {
                int index = s_Expression.indexOf(i), count = countCharacter(s_Expression, i);

                if(i != '-'){
                    if(count == 1 && (index == 0 || index == s_Expression.length() - 1)) {
                        s_Expression = s_Expression.replace(i + "", "").trim();
                        break;
                    }
                }
                else
                    if(count == 1 && index == s_Expression.length() - 1) {
                        s_Expression = s_Expression.substring(0, s_Expression.length());
                        break;
                    }

                s_Expression = replaceParts(s_Expression, calc(s_Tokens, i)).trim();
                s_Tokens = s_Expression.split(" ");
            }
        }

        if(allNumbers(s_Expression))
            if((int)Double.parseDouble(s_Expression) - Double.parseDouble(s_Expression) == 0)
                return (int)Double.parseDouble(s_Expression) + "";

        return s_Expression;
    }

    private boolean allNumbers(String expression)
    {
        boolean retVal = true; // "-ret-urn" "-val-ue"
        for(char i : expression.trim().toCharArray()) 
            retVal = retVal && NUMBERS.contains(i + "");
        return retVal;
    }

    //private void printArray(String token[]) {
    //    for(String i : token) System.out.println(i);
    //}

    private HashMap<String, Double> calc(String tokens[], char symbol, int index)
    {
        HashMap<String, Double> result = null;

        switch(symbol) {
            case '^': result = calcPower(tokens); break;
            case '/': result = calcDivision(tokens); break;
            case '*': result = calcMultiply(tokens); break;
            case '+': result = calcAddition(tokens); break;
            case '-': result = calcSubtract(tokens); break;
            case '|': result = calcModulus(tokens); break;
        }

        return result;
    }

    private HashMap<String, Double> calc(String tokens[], char symbol) {
        return calc(tokens, symbol, 0);
    }

    private HashMap<String, Double> calcPower(String tokens[])
    {
        HashMap<String, Double> calculated = new HashMap<String, Double>();

        for(int i = 0; i < tokens.length; i++) {
            if(tokens[i].equals("^")){
                double first = Double.parseDouble(tokens[Math.max(0, i - 1)]), second = Double.parseDouble(tokens[Math.min(tokens.length - 1, i + 1)]);
                calculated.put(tokens[i - 1] + " " + tokens[i] + " " + tokens[i + 1], Math.pow(first, second));
                break;
            }
        }

        return calculated;
    }

    private HashMap<String, Double> calcModulus(String tokens[])
    {
        HashMap<String, Double> calculated = new HashMap<String, Double>();

        for(int i = 0; i < tokens.length; i++) {
            if(tokens[i].equals("|")){
                double first = Double.parseDouble(tokens[Math.max(0, i - 1)]), second = Double.parseDouble(tokens[Math.min(tokens.length - 1, i + 1)]);
                calculated.put(tokens[i - 1] + " " + tokens[i] + " " + tokens[i + 1], first % second);
                break;
            }
        }

        return calculated;
    }

    private HashMap<String, Double> calcDivision(String tokens[])
    {
        HashMap<String, Double> calculated = new HashMap<String, Double>();

        for(int i = 0; i < tokens.length; i++) {
            if(tokens[i].equals("/")){
                double first = Double.parseDouble(tokens[Math.max(0, i - 1)]), second = Double.parseDouble(tokens[Math.min(tokens.length - 1, i + 1)]);
                calculated.put(tokens[i - 1] + " " + tokens[i] + " " + tokens[i + 1], first / second);
                break;
            }
        }

        return calculated;
    }

    private HashMap<String, Double> calcMultiply(String tokens[])
    {
        HashMap<String, Double> calculated = new HashMap<String, Double>();

        for(int i = 0; i < tokens.length; i++) {
            if(tokens[i].equals("*")){
                double first = Double.parseDouble(tokens[Math.max(0, i - 1)]), second = Double.parseDouble(tokens[Math.min(tokens.length - 1, i + 1)]);
                calculated.put(tokens[i - 1] + " " + tokens[i] + " " + tokens[i + 1], first * second);
                break;
            }
        }

        return calculated;
    }

    private HashMap<String, Double> calcAddition(String tokens[])
    {
        HashMap<String, Double> calculated = new HashMap<String, Double>();
        for(int i = 0; i < tokens.length; i++) {
            if(tokens[i].equals("+")) {
                double first = Double.parseDouble(tokens[Math.max(0, i - 1)]), second = Double.parseDouble(tokens[Math.min(tokens.length - 1, i + 1)]);
                calculated.put(tokens[i - 1] + " " + tokens[i] + " " + tokens[i + 1], first + second);
                break;
            }
        }
        return calculated;
    }

    private HashMap<String, Double> calcSubtract(String tokens[])
    {
        HashMap<String, Double> calculated = new HashMap<String, Double>();

        for(int i = 0; i < tokens.length; i++) {
            if(tokens[i].equals("-")){
                double first = Double.parseDouble(tokens[Math.max(0, i - 1)]), second = Double.parseDouble(tokens[Math.min(tokens.length - 1, i + 1)]);
                calculated.put(tokens[i - 1] + " " + tokens[i] + " " + tokens[i + 1], first - second);
                break;
            }
        }

        return calculated;
    }

    private String replaceParts(String expression, HashMap<String, Double> results) {
        for(String i : results.keySet()) expression = expression.replace(i, results.get(i) + "");
        return expression;
    }

    private int countCharacter(String expression, char ch)
    {
        int count = 0;
        for(int i = 0; i < expression.length(); i++)    count += (expression.charAt(i) == ch) ? 1 : 0;
        return count;
    }
}

// THE INTERACTABILITY OF THE APPLICATION.
class MouseListen extends MouseAdapter 
{
    private char symbol;
    private JTextField textField;
    private CalculatorGUI gui;

    public MouseListen(JTextField textField, char symbol, CalculatorGUI gui) { 
        this.textField = textField;
        this.symbol = symbol;
        this.gui = gui;
    }

    public void mouseClicked(MouseEvent e) { gui.lastPress.add(symbol + "");textField.setText(textField.getText() + symbol + ""); }

    public void mouseEntered(MouseEvent e) {
        textField.setText(textField.getText() + symbol + "");
    }

    public void mouseExited(MouseEvent e) {
        String text = textField.getText();
        textField.setText(text.substring(0, text.length() - 1));
    }
}

// FOR A ROUNDED JTEXTFIELD
class RJTextField extends JTextField
{
    private Shape shape;
    public RJTextField(int cols) {
        super(cols);
        setOpaque(false);
        // Add an empty border around us to compensate for
        // the rounded corners.
        setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
    }
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        // Paint a rounded rectangle in the background.
        g.setColor(getBackground());
        //g.drawRoundRect(0, 0, width, height, 8, 8);
        // Now call the superclass behavior to paint the foreground.
        super.paintComponent(g);
    }
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
           shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        }
        return shape.contains(x, y);
    }
}

// FOR VISUALIZING HISTORY OF CALCULATIONS
class HistoryVisualizer extends JFrame
{
    private HashMap<String, String> history = new HashMap<String, String>();
    CalculatorGUI calculator;
    public HistoryVisualizer(CalculatorGUI calculator)
    {
        this.setVisible(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.calculator = calculator;
        this.addWindowListener(new WindowsListener(){
            public void windowClosed(WindowEvent e){

            }
        });
    }

    public void addHistory(String equation, String result) {
        history.put(equation, result);
    }

    public void makeVisible()
    {
        calculator.setVisible(false);
        for(String i : history.keySet())
        {
            JLabel equationLabel = new JLabel(i);
            JLabel resultLabel = new JLabel(history.get(i));

            this.add(equationLabel);
            this.add(resultLabel);
        }
        this.setVisible(true);
    }

    public void makeInvisible()
    {
        calculator.setVisible(true);
        this.setVisible(false);
        this.removeAll();
    }
}