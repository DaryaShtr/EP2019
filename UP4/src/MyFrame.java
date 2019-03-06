import javax.print.attribute.*;
import javax.print.attribute.standard.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.*;
import java.util.*;

public class MyFrame extends JFrame implements Printable {

	int windowSize = 900;
    private MyFrame() {
        initComponents();
        this.setSize(windowSize, windowSize);
        File f = new File("src");
        if (f.exists()) {
            System.out.println(f.getAbsolutePath());
        }
        textLines = initTextLines(new File("E:\\УП\\UP4\\src\\Line.java"));
    }


    private void initComponents() {

        JPanel jPanel1 = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
            	Graphics2D gr = (Graphics2D)g;
    			gr.setColor(new Color (250, 128, 114));
    			MyStroke stroke = new MyStroke();
    			gr.setStroke(new BasicStroke(7.0f));
    			gr.draw(stroke.createStrokedShape(new Line(windowSize/2, windowSize/2, windowSize/3)));
            }
        };

        JMenuBar jMenuBar1 = new JMenuBar();
        JMenu jMenu2 = new JMenu();
        JMenuItem jMenuItem1 = new JMenuItem();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new Color(255, 255, 255));

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 279, Short.MAX_VALUE)
        );

        jMenu2.setText("Options");

        jMenuItem1.setText("Print");
        jMenuItem1.addActionListener(this::jMenuItem1ActionPerformed);
        jMenu2.add(jMenuItem1);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
        PrinterJob job = PrinterJob.getPrinterJob();
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        printRequestAttributeSet.add(Sides.DUPLEX);
        printRequestAttributeSet.add(OrientationRequested.LANDSCAPE);

        job.setPrintable(this);
        boolean ok = job.printDialog(printRequestAttributeSet);
        if (ok) {
            try {
                job.print(printRequestAttributeSet);
            } catch (PrinterException ex) {
                System.err.print(ex);
            }
        }

    }


    public static void main(String args[]) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        EventQueue.invokeLater(() -> new MyFrame().setVisible(true));
    }

    private String[] textLines;
    private int[] pageBreaks;

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        int y = 0;
        Font font = new Font("Serif", Font.PLAIN, 10);
        FontMetrics metrics = graphics.getFontMetrics(font);
        int lineHeight = metrics.getHeight();
        if (pageIndex == 0) {
            BufferedImage bufferedImageAll = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2DForImage = bufferedImageAll.createGraphics();
            this.printAll(graphics2DForImage);
            double scale = pageFormat.getWidth() / this.getWidth();
            int newWidth = (int) (this.getWidth() * scale / 2.5);
            int newHeight = (int) (this.getHeight() * scale / 2.5);
            Image scaledImage = bufferedImageAll.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            graphics.drawImage(scaledImage, (int) (pageFormat.getImageableWidth()/2- 100), (int) (pageFormat.getImageableHeight() / 4), null);
            graphics.drawString("Астроида", 190, 120);
            return PAGE_EXISTS;
        }

        if (pageBreaks == null) {
            int linesPerPage = (int) (pageFormat.getImageableHeight() / lineHeight);
            int numBreaks = (textLines.length - 1) / linesPerPage + 1;
            pageBreaks = new int[numBreaks];
            for (int k = 0; k < numBreaks; k++) {
                pageBreaks[k] = k * linesPerPage;
            }
        }

        if (pageIndex > pageBreaks.length) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2D = (Graphics2D) graphics;
        g2D.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        int start = pageBreaks[pageIndex - 1];
        int end = (pageIndex == pageBreaks.length) ? textLines.length : pageBreaks[pageIndex];
        for (int line = start; line < end; line++) {
            y += lineHeight;
            graphics.drawString(textLines[line], 0, y);
        }
        return PAGE_EXISTS;
    }


    private String[] initTextLines(File file) {
        ArrayList<String> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("file not found");
            return null;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return result.toArray(new String[result.size()]);
    }
}