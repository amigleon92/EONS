package Interfaces;

import EON.Utilitarios.*;
import EON.*;
import EON.Algoritmos.*;
import EON.Metricas.Metricas;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.*;

/**
 *
 * @author sGaleano - dBaez Frame que se encargad de la interfaz con el usurio y
 * realizar la simulacion de una Red Optica Elastica. Permite realizar una
 * simulacion teniendo: - Una topologia - Un conjunto de algoritmos. - Un tipo
 * de demanda que sera generada y guardada en un archivo.
 */
public class VentanaPrincipal_Defrag_ProAct extends javax.swing.JFrame {

    private Topologias Redes; // topologias disponibles

    private int tiempoTotal; // Iiempo que dura una simualcion
    String redSeleccionada;
    private double anchoFS; // ancho de un FS en los enlaces
    private int capacidadPorEnlace; // cantidad de FSs por enlace en la topologia elegida

    private int Erlang, rutas;
    private int Lambda, contBloqueos;
    private int HoldingTime; // Erlang / Lambda
    private int FsMinimo; // Cantidad mínima de FS por enlace
    private int FsMaximo; // Cantidad máxima de FS por enlace
    private double entropia, msi, bfr;
    private ArrayList<Integer> rutasEstablecidas;
//    private int cantidadRedes; //cantidad de redes exitentes en el Simulador
    ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////
    //private List algoritmosCompletosParaEjecutar;
    private List algoritmosCompletosParaGraficar;
    private int cantidadDeAlgoritmosRuteoSeleccionados;
    private int cantidadDeAlgoritmosTotalSeleccionados;

    public VentanaPrincipal_Defrag_ProAct() {
        initComponents();
        this.Redes = new Topologias(); // asignamos todas las topologias disponibles}

        /*No mostramos inicialmente los paneles que muestran los Resultados
         */
        //this.cantidadDeAlgoritmosRuteoSeleccionados = 0;
        this.cantidadDeAlgoritmosTotalSeleccionados = 0;
        //this.algoritmosCompletosParaEjecutar = new LinkedList();
        this.algoritmosCompletosParaGraficar = new LinkedList();
        this.setTitle("EON Simulator - Defragmentación ProActiva");

        setearRed(); // setea la red que aparece por defecto

        // Al inicio de cada Simulacion e+condemos los paneles de Resultado
        this.etiquetaTextoDemandasTotales.setVisible(false);
        this.etiquetaDemandasTotales.setVisible(false);
        this.etiquetaTextoBloqueosTotales.setVisible(false);
        this.etiquetaBloqueosTotales.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        listaAlgoritmosRuteo = new javax.swing.JList<>();
        botonEjecutarSimulacion = new javax.swing.JButton();
        etiquetaTopologia = new javax.swing.JLabel();
        etiquetaError = new javax.swing.JLabel();
        etiquetaCapacidadActual = new javax.swing.JLabel();
        etiquetaTiempoActual = new javax.swing.JLabel();
        spinnerTiempoSimulacion = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        etiquetaImagen = new javax.swing.JLabel();
        panelResultadosBloqueos = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        etiquetaTextoDemandasTotales = new javax.swing.JLabel();
        etiquetaDemandasTotales = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        spinnerErlang = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        textFieldCapacidadEnlace = new javax.swing.JTextField();
        etiquetaRSA1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        listaRedes = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        etiquetaAnchoFSActual1 = new javax.swing.JLabel();
        textFieldLambda = new javax.swing.JTextField();
        etiquetaAnchoFSActual2 = new javax.swing.JLabel();
        textFieldFSminimo = new javax.swing.JTextField();
        etiquetaAnchoFSActual3 = new javax.swing.JLabel();
        etiquetaAnchoFSActual4 = new javax.swing.JLabel();
        textFieldFSmaximo = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        textFieldAnchoFS = new javax.swing.JTextField();
        etiquetaAnchoFSActual = new javax.swing.JLabel();
        panelResultadosEntropia = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        panelResultadosMSI = new javax.swing.JPanel();
        panelResultadosBFR = new javax.swing.JPanel();
        panelResultadosLightPaths = new javax.swing.JPanel();
        panelResultadosTodos = new javax.swing.JPanel();
        etiquetaTextoBloqueosTotales = new javax.swing.JLabel();
        etiquetaBloqueosTotales = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        listaAlgoritmosRuteo.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "FA", "FA-CA", "" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listaAlgoritmosRuteo.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listaAlgoritmosRuteo.setToolTipText("");
        listaAlgoritmosRuteo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        listaAlgoritmosRuteo.setSelectedIndex(0);
        listaAlgoritmosRuteo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaAlgoritmosRuteoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(listaAlgoritmosRuteo);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 540, 112, 110));

        botonEjecutarSimulacion.setText("Simular");
        botonEjecutarSimulacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEjecutarSimulacionActionPerformed(evt);
            }
        });
        getContentPane().add(botonEjecutarSimulacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 610, 111, 46));

        etiquetaTopologia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        etiquetaTopologia.setText("Topologia");
        getContentPane().add(etiquetaTopologia, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 70, -1));
        getContentPane().add(etiquetaError, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 10, 420, 23));

        etiquetaCapacidadActual.setText("Capacidad");
        getContentPane().add(etiquetaCapacidadActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 110, 20));

        etiquetaTiempoActual.setText("Tiempo de Simulacion");
        getContentPane().add(etiquetaTiempoActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 140, 20));

        spinnerTiempoSimulacion.setModel(new javax.swing.SpinnerNumberModel(100, 50, 100000, 25));
        spinnerTiempoSimulacion.setToolTipText("");
        spinnerTiempoSimulacion.setRequestFocusEnabled(false);
        spinnerTiempoSimulacion.setValue(1000);
        getContentPane().add(spinnerTiempoSimulacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 70, 60, 20));

        jLabel2.setText("FSs por Enlace");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 150, -1, 20));
        getContentPane().add(etiquetaImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 330, 170));

        panelResultadosBloqueos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        panelResultadosBloqueos.setPreferredSize(new java.awt.Dimension(480, 200));
        panelResultadosBloqueos.setVerifyInputWhenFocusTarget(false);

        javax.swing.GroupLayout panelResultadosBloqueosLayout = new javax.swing.GroupLayout(panelResultadosBloqueos);
        panelResultadosBloqueos.setLayout(panelResultadosBloqueosLayout);
        panelResultadosBloqueosLayout.setHorizontalGroup(
            panelResultadosBloqueosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 476, Short.MAX_VALUE)
        );
        panelResultadosBloqueosLayout.setVerticalGroup(
            panelResultadosBloqueosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 196, Short.MAX_VALUE)
        );

        getContentPane().add(panelResultadosBloqueos, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 60, 480, 200));

        jLabel5.setText("Unidades");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 70, -1, 20));

        etiquetaTextoDemandasTotales.setText("Cantidad total de Demandas:");
        getContentPane().add(etiquetaTextoDemandasTotales, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 30, 170, 20));
        getContentPane().add(etiquetaDemandasTotales, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 30, 90, 20));

        jLabel4.setText("Carga de Trafico Maximo");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 370, 150, 20));

        spinnerErlang.setModel(new javax.swing.SpinnerNumberModel(100, 10, 1500, 100));
        getContentPane().add(spinnerErlang, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 370, 50, -1));

        jLabel6.setText("Erlang");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 370, 50, 20));

        textFieldCapacidadEnlace.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldCapacidadEnlace.setText("50");
        getContentPane().add(textFieldCapacidadEnlace, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 150, 60, -1));

        etiquetaRSA1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        etiquetaRSA1.setText("Algoritmo de Ruteo");
        getContentPane().add(etiquetaRSA1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 520, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setText("Otros");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, -1, -1));

        listaRedes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Red 1", "Red 2" }));
        listaRedes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listaRedesActionPerformed(evt);
            }
        });
        getContentPane().add(listaRedes, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 90, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setText("Defragmentación ProActiva");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setText("Resultados");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 20, -1, -1));

        etiquetaAnchoFSActual1.setText("Lambda");
        getContentPane().add(etiquetaAnchoFSActual1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 410, 110, 20));

        textFieldLambda.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldLambda.setText("5");
        textFieldLambda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldLambdaActionPerformed(evt);
            }
        });
        getContentPane().add(textFieldLambda, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 410, 40, 20));

        etiquetaAnchoFSActual2.setText("mínimo");
        getContentPane().add(etiquetaAnchoFSActual2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 450, 70, 20));

        textFieldFSminimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldFSminimo.setText("2");
        getContentPane().add(textFieldFSminimo, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 450, 40, 20));

        etiquetaAnchoFSActual3.setText("FS Rango");
        getContentPane().add(etiquetaAnchoFSActual3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 450, 70, 20));

        etiquetaAnchoFSActual4.setText("máximo");
        getContentPane().add(etiquetaAnchoFSActual4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 480, 70, 20));

        textFieldFSmaximo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldFSmaximo.setText("8");
        getContentPane().add(textFieldFSmaximo, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 480, 40, 20));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel12.setText("Red");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        jLabel3.setText("GHz");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 110, 30, 20));

        textFieldAnchoFS.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldAnchoFS.setText("2");
        textFieldAnchoFS.setEnabled(false);
        textFieldAnchoFS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldAnchoFSActionPerformed(evt);
            }
        });
        getContentPane().add(textFieldAnchoFS, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 110, 30, 20));

        etiquetaAnchoFSActual.setText("Ancho FS");
        getContentPane().add(etiquetaAnchoFSActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 110, 60, 20));

        panelResultadosEntropia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        javax.swing.GroupLayout panelResultadosEntropiaLayout = new javax.swing.GroupLayout(panelResultadosEntropia);
        panelResultadosEntropia.setLayout(panelResultadosEntropiaLayout);
        panelResultadosEntropiaLayout.setHorizontalGroup(
            panelResultadosEntropiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 476, Short.MAX_VALUE)
        );
        panelResultadosEntropiaLayout.setVerticalGroup(
            panelResultadosEntropiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 196, Short.MAX_VALUE)
        );

        getContentPane().add(panelResultadosEntropia, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 270, 480, 200));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, -1, 670));

        panelResultadosMSI.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        javax.swing.GroupLayout panelResultadosMSILayout = new javax.swing.GroupLayout(panelResultadosMSI);
        panelResultadosMSI.setLayout(panelResultadosMSILayout);
        panelResultadosMSILayout.setHorizontalGroup(
            panelResultadosMSILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 476, Short.MAX_VALUE)
        );
        panelResultadosMSILayout.setVerticalGroup(
            panelResultadosMSILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 196, Short.MAX_VALUE)
        );

        getContentPane().add(panelResultadosMSI, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 60, 480, 200));

        panelResultadosBFR.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        javax.swing.GroupLayout panelResultadosBFRLayout = new javax.swing.GroupLayout(panelResultadosBFR);
        panelResultadosBFR.setLayout(panelResultadosBFRLayout);
        panelResultadosBFRLayout.setHorizontalGroup(
            panelResultadosBFRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 476, Short.MAX_VALUE)
        );
        panelResultadosBFRLayout.setVerticalGroup(
            panelResultadosBFRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 196, Short.MAX_VALUE)
        );

        getContentPane().add(panelResultadosBFR, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 270, 480, 200));

        panelResultadosLightPaths.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        javax.swing.GroupLayout panelResultadosLightPathsLayout = new javax.swing.GroupLayout(panelResultadosLightPaths);
        panelResultadosLightPaths.setLayout(panelResultadosLightPathsLayout);
        panelResultadosLightPathsLayout.setHorizontalGroup(
            panelResultadosLightPathsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 476, Short.MAX_VALUE)
        );
        panelResultadosLightPathsLayout.setVerticalGroup(
            panelResultadosLightPathsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 196, Short.MAX_VALUE)
        );

        getContentPane().add(panelResultadosLightPaths, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 480, -1, 200));

        panelResultadosTodos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        javax.swing.GroupLayout panelResultadosTodosLayout = new javax.swing.GroupLayout(panelResultadosTodos);
        panelResultadosTodos.setLayout(panelResultadosTodosLayout);
        panelResultadosTodosLayout.setHorizontalGroup(
            panelResultadosTodosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 476, Short.MAX_VALUE)
        );
        panelResultadosTodosLayout.setVerticalGroup(
            panelResultadosTodosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 196, Short.MAX_VALUE)
        );

        getContentPane().add(panelResultadosTodos, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 480, -1, 200));

        etiquetaTextoBloqueosTotales.setText("Cantidad total de Bloqueos:");
        getContentPane().add(etiquetaTextoBloqueosTotales, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 30, 170, 20));
        getContentPane().add(etiquetaBloqueosTotales, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 30, 90, 20));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonEjecutarSimulacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEjecutarSimulacionActionPerformed

        // Al inicio de cada Simulacion e+condemos los paneles de Resultado
        //this.panelResultadosBloqueos.setVisible(false);
        this.etiquetaTextoDemandasTotales.setVisible(false);
        this.etiquetaDemandasTotales.setVisible(false);
        this.etiquetaTextoBloqueosTotales.setVisible(false);
        this.etiquetaBloqueosTotales.setVisible(false);

        //leemos los valores seteados
        this.tiempoTotal = Integer.parseInt(this.spinnerTiempoSimulacion.getValue().toString()); //Tiempo de simulacion indicado por el usuario
        this.redSeleccionada = (String) this.listaRedes.getSelectedItem(); // obtenemos la topologia seleccionada
        this.anchoFS = Double.parseDouble(this.textFieldAnchoFS.getText()); // ancho de los FSs de la toplogia elegida, tambien indicado por el usuario
        this.capacidadPorEnlace = Integer.parseInt(this.textFieldCapacidadEnlace.getText()); //obtenemos la cantidad de FS de los enlaces indicados por el usuario

        this.Erlang = Integer.parseInt(this.spinnerErlang.getValue().toString()); //obtenemos Erlang indicados por el usuario
        this.Lambda = Integer.parseInt(this.textFieldLambda.getText()); //obtenemos Erlang indicados por el usuario
        this.HoldingTime = (Erlang / Lambda); // Erlang / Lambda
        this.FsMinimo = Integer.parseInt(this.textFieldFSminimo.getText()); //obtenemos FSminimo indicados por el usuario
        this.FsMaximo = Integer.parseInt(this.textFieldFSmaximo.getText()); //obtenemos FSmaximo indicados por el usuario

        //Guardar el seleccionado en la lista de algoritmos seleccionados, más adelante ver como agregar más algoritmos a la lista
        List algoritmosRuteoSeleccionados = this.listaAlgoritmosRuteo.getSelectedValuesList();
        String algoritmoSeleccionado = (String) algoritmosRuteoSeleccionados.get(0);
        //System.out.println("El algoritmosRuteoSeleccionados22:"+algoritmoSeleccionado);
        if (algoritmoSeleccionado.equals("FA-CA")) {
            this.algoritmosCompletosParaGraficar.add(cantidadDeAlgoritmosTotalSeleccionados, "FA-CA");
            this.cantidadDeAlgoritmosTotalSeleccionados++;
        } else if (algoritmoSeleccionado.equals("FA")) {
            this.algoritmosCompletosParaGraficar.add(cantidadDeAlgoritmosTotalSeleccionados, "FA");
            this.cantidadDeAlgoritmosTotalSeleccionados++;
        }

        GrafoMatriz G[] = new GrafoMatriz[this.algoritmosCompletosParaGraficar.size()]; // Se tiene una matriz de adyacencia por algoritmo RSA elegidos para por el usuario

        Demanda d = new Demanda();  // Demanda a recibir
        File archivoDemandas = null;
        Resultado r = new Resultado(); // resultado obtenido en una demanda. Si r==null se cuenta como bloqueo
        String mensajeError = "Seleccione: "; // mensaje de error a mostrar eal usuario si no ha completado los parametros de
        // simulacion

        List<String> RSA = new LinkedList<String>(); // lista de Algoritmos RSA seleccionados
        ResultadoRuteo r1 = new ResultadoRuteo(); // resultado optenido luego de ejecutarse un algoritmo de ruteo

        int E = (int) this.spinnerErlang.getValue(); // se obtiene el limite de carga (Erlang) de trafico seleccionado por el usuario
        ArrayList<Demanda> demandasPorUnidadTiempo = new ArrayList<>(); //ArrayList que contiene las demandas para una unidad de tiempo T
        rutasEstablecidas = new ArrayList();
        int earlang = 0; //Carga de trafico en cada simulacion
        int k = -1; // contador auxiliar
        //int paso = (int) this.spinnerPaso.getValue(); // siguiente carga de trafico a simular (Erlang)
        int contD = 0; // contador de demandas totales
        int tiempoT = Integer.parseInt(this.spinnerTiempoSimulacion.getValue().toString()); // Tiempo de simulacion especificada por el usaurio
        int capacidadE = Integer.parseInt(this.textFieldCapacidadEnlace.getText().toString()); // espectro por enalce
        double anchoFS = Double.parseDouble(this.textFieldAnchoFS.getText().toString()); // ancho de FS
        //factor del tiempo de simulacion especificado por el usuario

        System.out.println("El ancho del FS es:" + anchoFS);
        System.out.println("Cantidad de FS por enlace:" + capacidadE);
        System.out.println("Cantidad Algoritmos:" + this.cantidadDeAlgoritmosTotalSeleccionados);

        //if(this.listaDemandas.getSelectedIndex()>=0 && this.listaAlgoritmosRuteo.getSelectedIndex()>=0 && 
        //        this.listaRedes.getSelectedIndex()>=0 && this.listaAlgoritmosAS.getSelectedIndex()>=0 && this.cantidadDeAlgoritmosTotalSeleccionados >0){ // si todos los parametros fueron seleccionados
        if (this.listaAlgoritmosRuteo.getSelectedIndex() >= 0 && this.listaRedes.getSelectedIndex() >= 0 && this.cantidadDeAlgoritmosTotalSeleccionados > 0) {
            this.etiquetaError.setVisible(true); // habilitamos la etiqueta de error

            RSA = this.algoritmosCompletosParaGraficar; // obtenemos los algoritmos RSA seleccionados

            //String demandaSeleccionada = this.listaDemandas.getSelectedValue(); // obtenemos el tipo de trafico seleccionado
            int[] conexid = new int[RSA.size()];

            for (int i = 0; i < conexid.length; i++) {
                conexid[i] = 0;
            }

            int[] contB = new int[RSA.size()]; // array encargado de almacenar en cada posicion la cantidad de bloqueo para cada
            // algoritmo seleccionado
            List prob[] = new LinkedList[RSA.size()]; // probabilidad de bloqueo para cada algoritmo RSA selecciondo 

            for (int i = 0; i < prob.length; i++) {
                prob[i] = new LinkedList(); // para cada algoritmo, se tiene una lista enlazada que almacenara la Pb 
                // obtenidad en cada simulacion
            }

            switch (redSeleccionada) { // cargamos los datos en las matrices de adyacencia segun la topologia seleccionada
                case "Red 0":
                    //de ´rueba no utilizado
                    for (int i = 0; i < RSA.size(); i++) {
                        G[i] = new GrafoMatriz(this.Redes.getRed(0).getCantidadDeVertices());
                        G[i].insertarDatos(this.Redes.getTopologia(0));
                    }
                    break;
                case "Red 1":
                    for (int i = 0; i < RSA.size(); i++) {
                        G[i] = new GrafoMatriz(this.Redes.getRed(1).getCantidadDeVertices());
                        G[i].insertarDatos(this.Redes.getTopologia(1));
                    }
                    break;
                case "Red 2":
                    for (int i = 0; i < RSA.size(); i++) {
                        G[i] = new GrafoMatriz(this.Redes.getRed(2).getCantidadDeVertices());
                        G[i].insertarDatos(this.Redes.getTopologia(2));
                    }
            }
            try {
                //while (earlang <= E) { // mientras no se llega a la cargad de trafico maxima
                archivoDemandas = Utilitarios.generarArchivoDemandas(Lambda, tiempoTotal, FsMinimo, FsMaximo, G[0].getCantidadDeVertices(), HoldingTime);
            } catch (IOException ex) {
                Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(Level.SEVERE, null, ex);
            }
            String ruta = System.getProperty("user.dir") + "Resultado" + Lambda + "k_" + tiempoTotal + "t-"+RSA.get(0)+".txt";
            File archivoResultados = new File(ruta);
            for (int i = 1; i <= tiempoT; i++) {
                try {
                    demandasPorUnidadTiempo = Utilitarios.leerDemandasPorTiempo(archivoDemandas, i); //lee las demandas para el tiempo i
                } catch (IOException ex) {
                    Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(Level.SEVERE, null, ex);
                }
                for (Demanda demanda : demandasPorUnidadTiempo) { // para cada demanda
                    ListaEnlazada[] ksp = Utilitarios.KSP(G[0], demanda.getOrigen(), demanda.getDestino(), 5); // calculamos los k caminos mas cortos entre el origen y el fin. Con k=5 (pude ser mas, cambiar dependiendo de la necesidad)
                    for (int a = 0; a < RSA.size(); a++) {

                        String algoritmoAejecutar = RSA.get(a);

                        switch (algoritmoAejecutar) {
                            case "FA":
                                r = Algoritmos_Defrag_ProAct.Def_FA(G[a], demanda, ksp, capacidadE);
                                if (r != null) {
                                    Utilitarios.asignarFS_Defrag(ksp, r, G[a], demanda, ++conexid[a]);
                                } else {
                                    contB[a]++;
                                    contBloqueos++;
                                }
                                break;
                            case "FA-CA":
                                r = Algoritmos_Defrag_ProAct.Def_FACA(G[a], demanda, ksp, capacidadE);
                                if (r != null) {
                                    Utilitarios.asignarFS_Defrag(ksp, r, G[a], demanda, ++conexid[a]);
                                    //rutasEstablecidas.add();
                                } else {
                                    contB[a]++;
                                    contBloqueos++;
                                }
                                break;
                        }

                    }
                    contD++;
                }
                for (int a = 0; a < RSA.size(); a++) {
                    //Escribimos el archivo de resultados
                    entropia = G[a].entropia();
                    msi = Metricas.MSI(G[a], capacidadE);
                    bfr = Metricas.BFR(G[a], capacidadE);
                    
                    try {
                        //Utilitarios.escribirArchivoResultados(archivoResultados, i, contBloqueos, contD, entropia, msi, bfr, 0);
                        Utilitarios.escribirArchivoResultados(archivoResultados, i, contBloqueos, contD, (double)((int)((entropia*100))/100), (double)(((int)(msi*100))/100), (double)(((int)(bfr*100))/100), 0);
                        //Utilitarios.escribirArchivoResultados(archivoResultados, i, contBloqueos, contD, (int) entropia, (int) msi, (int) bfr, 0);
                    } catch (IOException ex) {
                        Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                for (int j = 0; j < RSA.size(); j++) {
                    Utilitarios.Disminuir(G[j]);
                }
                contBloqueos = 0;
            }
            ++k;
            // almacenamos la probablidad de bloqueo final para cada algoritmo
            for (int a = 0; a < RSA.size(); a++) {
                prob[a].add(((double) contB[a] / contD));
                System.out.println("Probabilidad: " + (double) prob[a].get(k) + " Algoritmo: " + RSA.get(a));
            }
            // avanzamos a la siguiente carga de trafico
            //earlang += paso;
            //}
            this.etiquetaError.setText("Simulacion Terminada...");
            
            
            // una vez finalizado, graficamos el resultado.
            //leemos el archivo de resultados
            String linea;
            int contLinea = 0;
            XYSeries series[] = new XYSeries[5];
            XYSeriesCollection datos = new XYSeriesCollection();

            FileReader fr;
            try {
                fr = new FileReader(archivoResultados);
                BufferedReader br = new BufferedReader(fr);
                series[0] = new XYSeries("Lista de Bloqueos");
                series[1] = new XYSeries("Entropía");
                series[2] = new XYSeries("MSI");
                series[3] = new XYSeries("BFR");
                series[4] = new XYSeries("Cantidad de Light Paths");
                
                while (((linea = br.readLine()) != null)) {
                    contLinea++;
                    String[] line = linea.split(",", 7);
               
                    series[0].add(contLinea, (double) Double.parseDouble(line[2]));
                    series[1].add(contLinea, (double) Double.parseDouble(line[3]));
                    series[2].add(contLinea, (double) Double.parseDouble(line[4]));
                    series[3].add(contLinea, (double) Double.parseDouble(line[5]));
                    series[4].add(contLinea, (double) Double.parseDouble(line[6]));
                }
                        
                datos.addSeries(series[0]);
                Utilitarios.GraficarResultado(datos, "Bloqueos", this.panelResultadosBloqueos);
                datos = new XYSeriesCollection();
                
                datos.addSeries(series[1]);
                Utilitarios.GraficarResultado(datos, "Entropía", this.panelResultadosEntropia);
                datos = new XYSeriesCollection();
                
                datos.addSeries(series[2]);
                Utilitarios.GraficarResultado(datos, "MSI", this.panelResultadosMSI);
                datos = new XYSeriesCollection();
                
                datos.addSeries(series[3]);
                Utilitarios.GraficarResultado(datos, "BFR", this.panelResultadosBFR);
                datos = new XYSeriesCollection();

                datos.addSeries(series[4]);
                Utilitarios.GraficarResultado(datos, "Cantidad de Light Paths", this.panelResultadosLightPaths);
                datos = new XYSeriesCollection();
                
                datos.addSeries(series[0]);
                datos.addSeries(series[1]);
                datos.addSeries(series[2]);
                datos.addSeries(series[3]);
                datos.addSeries(series[4]);
                Utilitarios.GraficarResultado(datos, "Todos", this.panelResultadosTodos);
                
            } catch (IOException ioe) {
                Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(Level.SEVERE, null, ioe);
            }
            

            //Utilitarios.GraficarResultado(prob, this.panelResultado, "Resultado de la Simulación", RSA, paso);
            String demandasTotales = "" + contD; // mostramos la cantidad de demandas totales recibidas
            this.etiquetaDemandasTotales.setText(demandasTotales);
            // this.etiquetaBloqueosTotales.setText(contB[a]); //VE DIEGO PORFA SI PODES HACER QUE FUNCIONE ESTA LINEA JE
            this.etiquetaTextoDemandasTotales.setVisible(true);
            this.etiquetaDemandasTotales.setVisible(true);
            this.etiquetaTextoBloqueosTotales.setVisible(true);
            this.etiquetaBloqueosTotales.setVisible(true);

            ////////Vaciar listas para las siguientes simulaciones///////////////
            /////////////////////////////////////////////////////////////////////
            //this.algoritmosCompletosParaEjecutar.clear();
            //this.algoritmosCompletosParaGraficar.clear();
            //this.cantidadDeAlgoritmosRuteoSeleccionados = 0;
            this.cantidadDeAlgoritmosTotalSeleccionados = 0;

        } else { // control de errores posibles realizados al no completar los parametros de simulacion
            if (this.listaAlgoritmosRuteo.getSelectedIndex() < 0) {
                if (mensajeError == "Seleccione ") {
                    mensajeError = mensajeError + "Algoritmo RSA";
                } else {
                    mensajeError = mensajeError + ", Algoritmo RSA";
                }
            }
            if (this.listaRedes.getSelectedIndex() < 0) {
                if (mensajeError == "Seleccione ") {
                    mensajeError = mensajeError + "Topologia";
                } else {
                    mensajeError = mensajeError + ", Topologia";
                }
            }
            if (mensajeError != "Seleccione ") {
                this.etiquetaError.setText(mensajeError);
            }
        }
    }//GEN-LAST:event_botonEjecutarSimulacionActionPerformed

    private void listaAlgoritmosRuteoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaAlgoritmosRuteoMouseClicked
        // TODO add your handling code here:
//        List algoritmosRuteoSeleccionados = this.listaAlgoritmosRuteo.getSelectedValuesList();
//        String algoritmoSeleccionado = (String) algoritmosRuteoSeleccionados.get(0);
//        //System.out.println("El algoritmosRuteoSeleccionados22:"+algoritmoSeleccionado);
//        if (algoritmoSeleccionado.equals("FAR")) {
//            this.panelAsignacionSpectro.setVisible(true);
//        } else {
//            this.panelAsignacionSpectro.setVisible(false);
//        }


    }//GEN-LAST:event_listaAlgoritmosRuteoMouseClicked

    private void listaRedesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listaRedesActionPerformed
        setearRed();
    }//GEN-LAST:event_listaRedesActionPerformed

    private void setearRed() {
        if (this.listaRedes.getSelectedIndex() >= 0) {

            ImageIcon Img = new ImageIcon(getClass().getResource("Imagenes/" + (listaRedes.getSelectedItem() + ".png")));
            etiquetaImagen.setIcon(Img);
            etiquetaImagen.setBounds(210, 100, 320, 170);
            etiquetaImagen.setVisible(true);
            etiquetaImagen.setOpaque(false);
            String redseleccionada = (String) this.listaRedes.getSelectedItem();
            switch (redseleccionada) {
                case "Red 1":
                    this.textFieldCapacidadEnlace.setText(Integer.toString((int) (this.Redes.getRed(1).getCapacidadTotal() / this.Redes.getRed(1).getAnchoFS())));
                    this.textFieldAnchoFS.setText(Double.toString(this.Redes.getRed(1).getAnchoFS()));
                    break;
                case "Red 2":
                    this.textFieldCapacidadEnlace.setText(Integer.toString((int) (this.Redes.getRed(2).getCapacidadTotal() / this.Redes.getRed(1).getAnchoFS())));
                    this.textFieldAnchoFS.setText(Double.toString(this.Redes.getRed(2).getAnchoFS()));
                    break;
            }
        }
    }
    private void textFieldLambdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldLambdaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldLambdaActionPerformed

    private void textFieldAnchoFSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldAnchoFSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldAnchoFSActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal_Defrag_ProAct().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonEjecutarSimulacion;
    private javax.swing.JLabel etiquetaAnchoFSActual;
    private javax.swing.JLabel etiquetaAnchoFSActual1;
    private javax.swing.JLabel etiquetaAnchoFSActual2;
    private javax.swing.JLabel etiquetaAnchoFSActual3;
    private javax.swing.JLabel etiquetaAnchoFSActual4;
    private javax.swing.JLabel etiquetaBloqueosTotales;
    private javax.swing.JLabel etiquetaCapacidadActual;
    private javax.swing.JLabel etiquetaDemandasTotales;
    private javax.swing.JLabel etiquetaError;
    private javax.swing.JLabel etiquetaImagen;
    private javax.swing.JLabel etiquetaRSA1;
    private javax.swing.JLabel etiquetaTextoBloqueosTotales;
    private javax.swing.JLabel etiquetaTextoDemandasTotales;
    private javax.swing.JLabel etiquetaTiempoActual;
    private javax.swing.JLabel etiquetaTopologia;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JList<String> listaAlgoritmosRuteo;
    private javax.swing.JComboBox<String> listaRedes;
    private javax.swing.JPanel panelResultadosBFR;
    private javax.swing.JPanel panelResultadosBloqueos;
    private javax.swing.JPanel panelResultadosEntropia;
    private javax.swing.JPanel panelResultadosLightPaths;
    private javax.swing.JPanel panelResultadosMSI;
    private javax.swing.JPanel panelResultadosTodos;
    private javax.swing.JSpinner spinnerErlang;
    private javax.swing.JSpinner spinnerTiempoSimulacion;
    private javax.swing.JTextField textFieldAnchoFS;
    private javax.swing.JTextField textFieldCapacidadEnlace;
    private javax.swing.JTextField textFieldFSmaximo;
    private javax.swing.JTextField textFieldFSminimo;
    private javax.swing.JTextField textFieldLambda;
    // End of variables declaration//GEN-END:variables

}
