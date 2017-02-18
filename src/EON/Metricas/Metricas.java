/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EON.Metricas;
import EON.*;
import EON.Utilitarios.*;
import java.util.ArrayList;

/**
 *
 * @author sFernandez
 */
public class Metricas {
    
    /*
     Bandwidth Fragmentation Ratio - Mide la cantidad de bloques de FS por enlace
    @param
        G - red actual
        capacidad - cantdiad de FS por enlace
    
    */
    public static double BFR(GrafoMatriz G, int capacidad){
        ArrayList<Integer> lista = new ArrayList<>();
        double contOcupados = 0;
        double contSeguido = 0;
        double mayorSeguido = 0;
        double sumaEnlaces = 0;
        double [][] maxBlocks = new double[G.getCantidadDeVertices()][G.getCantidadDeVertices()];
        for (int i=0; i<G.getCantidadDeVertices(); i++){
            for (int j=0 ;j<G.getCantidadDeVertices() ; j++){
                if (!Utilitarios.isInList(lista, j)){
                    if (G.acceder(i, j)!=null){
                        for (int k=0; k<capacidad; k++){
                            //1= libre 0 = Ocupado
                            if (G.acceder(i, j).getFS()[k].getEstado()==1){
                                contSeguido++;
                            }else{
                                if (contSeguido>mayorSeguido){
                                    mayorSeguido = contSeguido;
                                }
                                contSeguido = 0;
                                contOcupados++;
                            }
                        }
                        if (contSeguido>mayorSeguido){
                            mayorSeguido = contSeguido;
                        }
                        
                        if (mayorSeguido==0){
                            System.out.println("");
                        }

                        if (contOcupados==capacidad){
                            maxBlocks[i][j] = 0;
                        }else{
                            maxBlocks[i][j] = 1 - (mayorSeguido/(capacidad-contOcupados));
                        }
                        
                        if (maxBlocks[i][j]==1){
                            System.out.println("MaxBlock= "+mayorSeguido + " / (352 - subm(b) : "+ contOcupados);
                        }

                    }else{
                        maxBlocks[i][j] = -1;
                    }
                    contOcupados = 0;
                    mayorSeguido = 0;
                    contSeguido = 0;
                }
            }
            lista.add(i);
        }
        
        lista = new ArrayList<>();
        
        for (int i=0; i<G.getCantidadDeVertices(); i++){
            for (int j=0; j<G.getCantidadDeVertices(); j++){
                if (!Utilitarios.isInList(lista, j) && maxBlocks[i][j]!=-1 ){
                    
                    sumaEnlaces = sumaEnlaces + maxBlocks[i][j];
                    
                }
            }
            lista.add(i);
        }
        if (sumaEnlaces==G.getCantidadEnlaces()){
            System.out.println("");
        }

        return (sumaEnlaces/G.getCantidadEnlaces());
        
        
        
    }
    
    /*
     Maximu Used Index-Slot retorna el mayor indice usado en un enlace
    @param
        G - red actual
        capacidad - cantidad de FS por enlace
    */
    public static double MSI(GrafoMatriz G, int capacidad){
        ArrayList<Integer> lista = new ArrayList<>();
        double cont = 0;
        double sumaIndices = 0;
        //ArrayList<Integer> indices = new ArrayList<>();
        double [] indices = new double[G.getCantidadEnlaces()];
        int c= 0;
        
        for (int i=0; i<G.getCantidadEnlaces(); i++){
            indices[i] = 0;
        }
        for (int i=0; i<G.getCantidadDeVertices(); i++){
            for (int j=0; j<G.getCantidadDeVertices(); j++){
                if (G.acceder(i, j)!=null){
                    if (!Utilitarios.isInList(lista, j)){
                        for (int k=capacidad-1; k>-1; k--){
                            // 1 = libre 0 = Ocupado
                            if (G.acceder(i, j).getFS()[k].getEstado()==0){
                                //indices.add(k);
                                indices[c] = k;
                                c++;
                                break;
                            }
                        }
                    }
                }
            }
            lista.add(i);
        }
        

        
        for (int i=0; i<indices.length; i++){
            sumaIndices = sumaIndices + indices[i];
        }
        
        return (sumaIndices/G.getCantidadEnlaces());
    }
    
}
