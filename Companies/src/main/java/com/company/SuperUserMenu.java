package com.company;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SuperUserMenu extends JFrame {

    public class MyComponent extends JComponent {
        int x, y, height, width, angle;
        public MyComponent(int x, int y, int width, int height, int angle) {
            this.x = x;
            this.y = y;
            this.height = height;
            this.width = width;
            this.angle = angle;
        }
        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(Color.GREEN);
            g.fillOval(x, y, width, height);
            g.setColor(Color.RED);
            g.fillArc(x, y, width, height, 0, angle);
        }
    }

    public class MyComponentForStatistic extends JComponent {
        int x, y, height, width, angle;
        Color color[] = null;
        Magazyn magazyn[] = null;
        public MyComponentForStatistic(int x, int y, int width, int height, Color[] color, Magazyn[] magazyn) {
            this.x = x;
            this.y = y;
            this.height = height;
            this.width = width;
            this.color = color;
            this.magazyn = magazyn;
        }
        @Override
        protected void paintComponent(Graphics g) {
            int count = 0;
            for(int j = 0; j < magazyn.length; j++){
                count += magazyn[j].przedmiotArrayList.size();
            }
            int last_angle = 0;
            System.out.println("Count " + count);
            for(int i = 0; i < magazyn.length; i++){
                int procent = 100-((magazyn[i].przedmiotArrayList.size()*100)/count);
                System.out.println("procent " + procent);
                int angle = (procent*360-last_angle)/100;
                g.setColor(color[i]);
                g.fillArc(x, y, width, height, 0, angle);
                last_angle = angle;
            }
        }
    }

    private class SubjectShop extends JFrame{

        SubjectShop(){
            JPanel panel = new JPanel();
            JPanel panel1 = new JPanel();

            GridLayout layout = new GridLayout(1,2);

            panel.setLayout(layout);


            DefaultListModel<Magazyn> dlmMagazyn = new DefaultListModel<Magazyn>();

            for (int i = 0; i < Magazyn.listaMagazynow.size(); i++) {
                dlmMagazyn.addElement(Magazyn.listaMagazynow.get(i));
            }

            JList<Magazyn> shopJList = new JList<Magazyn>();
            shopJList.setModel(dlmMagazyn);
            JScrollPane shopScroll = new JScrollPane(shopJList);



            DefaultListModel<Przedmiot> dlmProduct = new DefaultListModel<Przedmiot>();

            for (int i = 0; i < Przedmiot.listaRzerzej.size(); i++) {
                dlmProduct.addElement(Przedmiot.listaRzerzej.get(i));
            }

            JList<Przedmiot> productJList = new JList<Przedmiot>();
            productJList.setModel(dlmProduct);
            JScrollPane productScroll = new JScrollPane(productJList);

            JButton dodacz = new JButton("Dodacz");


            shopScroll.setPreferredSize(new Dimension(300,300));
            productScroll.setPreferredSize(new Dimension(300,300));
            panel.add(shopScroll);
            panel.add(productScroll);

            getContentPane().add(panel);

            panel1.setLayout(new FlowLayout(FlowLayout.CENTER));
            panel1.add(dodacz);
            getContentPane().add(panel1, BorderLayout.SOUTH);

            dodacz.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            Magazyn magazyn = null;
                            magazyn = shopJList.getSelectedValue();
                            if(magazyn == null){
                                JOptionPane.showMessageDialog(getContentPane(), "Choose shop");
                                return;
                            }

                            Przedmiot przedmiot = null;
                            przedmiot = productJList.getSelectedValue();
                            if(przedmiot == null){
                                JOptionPane.showMessageDialog(getContentPane(), "Choose subject");
                                return;
                            }

                            if((magazyn.getFreeSpace(magazyn) - przedmiot.powierzchnia) < 0){
                                JOptionPane.showMessageDialog(getContentPane(), "Nie ma miejsca");
                                return;
                            }

                            magazyn.przedmiotArrayList.add(przedmiot);
                            setVisible(false);
                            dispose();
                        }
                    }
            );


            setLocationRelativeTo(null);

            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension dimension = toolkit.getScreenSize();
            setBounds(dimension.width/2-400, dimension.height/2-300, 800, 600);

            setVisible(true);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            pack();
        }

    }

    private class seeStatistic extends JFrame {

        seeStatistic(){
            JPanel all_panels = new JPanel();

            GridLayout layout = new GridLayout(1,2);
            all_panels.setLayout(layout);

            DefaultListModel<Magazyn> dlmMagazyn = new DefaultListModel<Magazyn>();

            for (int i = 0; i < Magazyn.listaMagazynow.size(); i++) {
                dlmMagazyn.addElement(Magazyn.listaMagazynow.get(i));
            }

            JList<Magazyn> shopJList = new JList<Magazyn>();
            shopJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
            shopJList.setModel(dlmMagazyn);
            JScrollPane shopScroll = new JScrollPane();
            shopScroll.setViewportView(shopJList);

            all_panels.add(shopScroll);

            Color list[] = {
                    Color.GREEN, Color.RED, Color.BLACK, Color.BLUE, Color.YELLOW, Color.PINK
            };

            JPanel panel_color = new JPanel();


            Container container = new Container();
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

            JPanel panel_colors = new JPanel();

            JScrollPane scroll = new JScrollPane();

            all_panels.add(scroll);



            shopJList.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                       if (e.getClickCount() == 1) {

                           container.removeAll();
                           container.remove(panel_color);
                           container.remove(panel_colors);
                           panel_colors.removeAll();
                           all_panels.remove(scroll);

                           Magazyn magazyny[] = shopJList.getSelectedValuesList().toArray(new Magazyn[0]);
                           int count = 0;
                           for (int i =0; i< magazyny.length; i++){

                               if(i < 6) {

                                   for(int j = 0; j < magazyny.length; j++){
                                       count += magazyny[j].przedmiotArrayList.size();
                                   }

                                   int finalI = i;
                                   JComponent mojComp = new JComponent() {
                                       public void paintComponent(Graphics g) {
                                           g.setColor(list[finalI]);
                                           g.fillRect(0, 0, 10, 10);
                                       }
                                   };
                                   mojComp.setPreferredSize(new Dimension(10, 10));
                                   panel_colors.add(mojComp, BorderLayout.NORTH);
                                   panel_colors.add(new JLabel("-"+magazyny[i].nazwa), BorderLayout.NORTH);



                                   container.add(panel_color);

                               } else {
                                   JOptionPane.showMessageDialog(getContentPane(), "Selected items cannot be more than 6");
                                   return;
                               }
                           }
                           int x = getContentPane().getSize().width/3/3;

                           MyComponentForStatistic myComponentForStatistic = new MyComponentForStatistic(x, 0, 100,100, list, magazyny);
                           myComponentForStatistic.setPreferredSize(new Dimension(100,100));

                           container.add(panel_colors, BorderLayout.NORTH);

                           container.add(myComponentForStatistic);



                           scroll.setViewportView(container);

                           all_panels.add(scroll);

                           all_panels.revalidate();
                           all_panels.repaint();
                       }
                   }
               }
            );

            getContentPane().add(all_panels);

            Toolkit toolkit = Toolkit.getDefaultToolkit();

            Dimension dimension = toolkit.getScreenSize();
            setBounds(dimension.width/2-400, dimension.height/2-300, 800, 600);

            setLocationRelativeTo(null);
            setVisible(true);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            pack();
        }
    }

    private class seeInfo extends JFrame{

        seeInfo(){
            JPanel all_panels = new JPanel();

            JLabel shop = new JLabel("Shop");
            JLabel subject = new JLabel("Subject");
            JLabel stat = new JLabel("Statistic");

            JPanel panel_names = new JPanel();

            panel_names.setLayout( new GridLayout(1,3));

            panel_names.add(shop);


            getContentPane().add(panel_names, BorderLayout.NORTH);

            GridLayout layout = new GridLayout(1,3);
            all_panels.setLayout(layout);

            DefaultListModel<Magazyn> dlmMagazyn = new DefaultListModel<Magazyn>();

            for (int i = 0; i < Magazyn.listaMagazynow.size(); i++) {
                dlmMagazyn.addElement(Magazyn.listaMagazynow.get(i));
            }

            JList<Magazyn> shopJList = new JList<Magazyn>();
            shopJList.setModel(dlmMagazyn);
            JScrollPane shopScroll = new JScrollPane();
            shopScroll.setViewportView(shopJList);



            all_panels.add(shopScroll);


            JScrollPane productScroll = new JScrollPane();
            all_panels.add(productScroll);

            shopScroll.setPreferredSize(new Dimension(400,300));
            productScroll.setPreferredSize(new Dimension(400,300));


            JPanel panel_color = new JPanel();

            Container container = new Container();
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

            JScrollPane statScroll = new JScrollPane();



            JComponent mojComp = new JComponent() {
                public void paintComponent(Graphics g) {
                    g.setColor(Color.RED);
                    g.fillRect(0, 0, 10, 10);
                }
            };


            mojComp.setPreferredSize(new Dimension(10,10));
            panel_color.add(mojComp, BorderLayout.NORTH);
            panel_color.add(new JLabel(" - Zajete"));



            JComponent mojComp1 = new JComponent() {
                public void paintComponent(Graphics g) {
                    g.setColor(Color.GREEN);
                    g.fillRect(0, 0, 10, 10);
                }
            };

            mojComp1.setPreferredSize(new Dimension(10,10));
            panel_color.add(mojComp1, BorderLayout.NORTH);
            panel_color.add(new JLabel(" - Wolne"));


            statScroll.setPreferredSize(new Dimension(400,300));
            container.add(panel_color);



            shopJList.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 1) {
                        all_panels.remove(productScroll);
                        all_panels.remove(statScroll);
                        container.removeAll();

                        Magazyn magazyn = null;
                        magazyn = shopJList.getSelectedValue();

                        System.out.println("Yes");
                        DefaultListModel<Przedmiot> dlmProduct = new DefaultListModel<Przedmiot>();

                        for (int i = 0; i < magazyn.przedmiotArrayList.size(); i++) {
                            System.out.println("I: " + i);
                            System.out.println(magazyn.przedmiotArrayList.get(i));
                            dlmProduct.addElement(magazyn.przedmiotArrayList.get(i));
                        }

                        JList<Przedmiot> productJList = new JList<Przedmiot>();
                        productJList.setModel(dlmProduct);
                        int procent = 100-((magazyn.getFreeSpace(magazyn)*100)/magazyn.powierzchnia);
                        int angle = procent*360/100;
                        int x = getContentPane().getSize().width/3/3;
                        MyComponent myComponent = new MyComponent(x, 0, 100, 100, angle);
                        myComponent.setPreferredSize(new Dimension(100,100));


                        String string = "Wolne - " + (100 - procent)+"%; Zajete - " + procent + "%";


                        container.add(panel_color);

                        container.add(myComponent);

                        container.add(new JLabel(string));
                        statScroll.setViewportView(container);
                        //statScroll.setViewportView(container);

                        productScroll.setViewportView(productJList);



                        all_panels.add(productScroll);
                        all_panels.add(statScroll);

                        panel_names.add(subject);
                        panel_names.add(stat);

                        all_panels.revalidate();
                        all_panels.repaint();
                    }
                }
            });

            getContentPane().add(all_panels);

            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension dimension = toolkit.getScreenSize();
            setBounds(dimension.width/2-400, dimension.height/2-300, 800, 600);

            setLocationRelativeTo(null);
            setVisible(true);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            pack();
        }
    }

    public SuperUserMenu(String name){

        JPanel panel = new JPanel();

        JFileChooser fileChooser = new JFileChooser();

        UIManager.put(
                "FileChooser.saveButtonText", "Save");
        UIManager.put(
                "FileChooser.cancelButtonText", "Сancel");
        UIManager.put(
                "FileChooser.fileNameLabelText", "File name");
        UIManager.put(
                "FileChooser.filesOfTypeLabelText", "File type");
        UIManager.put(
                "FileChooser.lookInLabelText", "Directory");
        UIManager.put(
                "FileChooser.saveInLabelText", "Save");
        UIManager.put(
                "FileChooser.folderNameLabelText", "Path");

        JButton shop = new JButton("Magazyny");
        JButton subject = new JButton("Przedmioty");
        JButton subject_shop = new JButton("Dodacz Przedmiot do Magazynu");
        JButton shop_info = new JButton("Info Magazynu");
        JButton stat = new JButton("Statystyka");
        panel.setSize(575,100);

        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.add(shop);
        panel.add(subject);
        //panel.add(subject_shop);
        getContentPane().add(panel, BorderLayout.NORTH);



        JPanel panel1 = new JPanel();
        panel.setSize(575,300);
        JTable table = new JTable(new Magazyn());



        JScrollPane scrollPane = new JScrollPane(table);

        panel1.add(scrollPane, BorderLayout.CENTER);
        JButton add_magazyn = new JButton("Dodacz magazyn");
        JButton add_magazyn_from_file = new JButton("Wygeneruj liste magazynow");
        panel1.add(add_magazyn, BorderLayout.SOUTH);
        panel1.add(add_magazyn_from_file, BorderLayout.SOUTH);
        getContentPane().add(panel1);

        JPanel panel2 = new JPanel();
        panel.setSize(575,300);
        JTable table1 = new JTable();
        table1.setModel(new Przedmiot());
        //table1.setFillsViewportHeight(true);
        JScrollPane scrollPane1 = new JScrollPane(table1);
        panel2.add(scrollPane1, BorderLayout.CENTER);
        JButton add_przedmiot = new JButton("Dodacz przedmiot");
        JButton add_przedmiot_from_file = new JButton("Wygeneruj liste przedmiotow");

        panel2.add(add_przedmiot, BorderLayout.SOUTH);
        panel2.add(add_przedmiot_from_file, BorderLayout.SOUTH);

        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel3.add(stat, BorderLayout.SOUTH);
        panel3.add(subject_shop);
        panel3.add(shop_info);
        getContentPane().add(panel3, BorderLayout.SOUTH);



        shop.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        getContentPane().remove(panel2);
                        getContentPane().add(panel1);
                        getContentPane().revalidate();
                        getContentPane().repaint();
                    }
                }
        );
        subject.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        getContentPane().remove(panel1);
                        getContentPane().add(panel2);
                        getContentPane().revalidate();
                        getContentPane().repaint();
                    }
                }
        );

        stat.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        new seeStatistic();
                    }
                }
        );

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Excel",  "xlsx");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(filter);

        add_magazyn_from_file.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        fileChooser.setDialogTitle("Choose directory");
                        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                        int result = fileChooser.showSaveDialog(getContentPane());
                        if (result == JFileChooser.APPROVE_OPTION ){
                            try {
                                new ExcelFilesGenerator(new Magazyn(), fileChooser.getSelectedFile());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                            JOptionPane.showMessageDialog(getContentPane(),
                                    fileChooser.getSelectedFile());
                    }
                }
        );

        add_przedmiot_from_file.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        fileChooser.setDialogTitle("Choose directory");
                        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                        int result = fileChooser.showSaveDialog(getContentPane());
                        if (result == JFileChooser.APPROVE_OPTION ){
                            try {
                                new ExcelFilesGenerator(new Przedmiot(), fileChooser.getSelectedFile());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        JOptionPane.showMessageDialog(getContentPane(),
                                fileChooser.getSelectedFile());
                    }
                }
        );

        subject_shop.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        new SubjectShop();
                    }
                }
        );

        add_magazyn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        Magazyn.Window();
                    }
                }
        );

        shop_info.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        new seeInfo();
                    }
                }
        );

        add_przedmiot.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        Przedmiot.Window();
                    }
                }
        );


        pack();



        setSize(575, 575);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Runnable r = ()->{
            System.out.printf("%s started... \n", Thread.currentThread().getName());
            int sum = 0;
            try{
                int last_size_shops = Magazyn.listaMagazynow.size();
                int last_size_subject = Przedmiot.listaRzerzej.size();
                while (true){

                    int s = 0;

                    for(int i = 0; i < Magazyn.listaMagazynow.size(); i++){
                        for(int j = 0; j < Magazyn.listaMagazynow.get(i).przedmiotArrayList.size(); j++){
                            s += 1;
                        }
                    }
                    if(Magazyn.listaMagazynow.size() > last_size_shops || s != sum){
                        getContentPane().remove(panel1);
                        getContentPane().add(panel1);
                        getContentPane().revalidate();
                        getContentPane().repaint();
                        last_size_shops = Magazyn.listaMagazynow.size();
                    }
                    if(Przedmiot.listaRzerzej.size() > last_size_subject){
                        getContentPane().remove(panel2);
                        getContentPane().add(panel2);
                        getContentPane().revalidate();
                        getContentPane().repaint();
                        last_size_subject = Przedmiot.listaRzerzej.size() ;
                    }
                    Thread.sleep(100);
                }
            }
            catch(InterruptedException e){
                System.out.println("Thread has been interrupted");
            }
            System.out.printf("%s finished... \n", Thread.currentThread().getName());
        };
        Thread myThread = new Thread(r, "Thread");
        myThread.start();
    }
}