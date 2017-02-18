
package EON.Algoritmos;

import EON.Demanda;
import EON.FrecuencySlots;
import EON.GrafoMatriz;
import EON.ListaEnlazada;
import EON.Nodo;
import EON.Resultado;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import EON.Utilitarios.*;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 *
 * @author Fernando
 */
public class Algoritmos {
    
    public static Resultado MPSC_Algorithm(GrafoMatriz G, Demanda demanda,ListaEnlazada [] ksp,int capacidad){
        int k=0;
        int ban=0;
        float cp;
        int OE[]=new int [(capacidad)];
        int bloque1=0,bloque2=0;
        Resultado r=new Resultado();
        while (ksp.length>k && ksp[k]!=null){
            for(int w=0;w<(capacidad);w++){
                OE[w]=1;
            }
            Nodo t=ksp[k].getInicio();
            int Total=(G.acceder(t.getDato(),t.getSiguiente().getDato())).getFS().length;
            float mayorcp=0;
            for(int j=0;j<Total;j++){
                for(t=ksp[k].getInicio();t.getSiguiente().getSiguiente()!=null;t=t.getSiguiente()){
                    if(G.acceder(t.getDato(),t.getSiguiente().getDato()).getFS()[j].getEstado()==0){
                       OE[j]=0;
                       break;
                    } 
                }
            }
            int vector[]=new int[2];
            List<int[]> BloquesE = new LinkedList<int[]>();
            int cgb=0;//contador global de bloques 
            for (int c=0;c<OE.length;c++){//de cuanto y cuantos bloques espectrales libres hay
                int i=0,f=0;
                if(OE[c]==1){
                    cgb++;
                    i=f=c;
                    for(;c<OE.length;c++){
                        if(OE[c]==0 || c==OE.length-1){
                            f=c;
                        }
                    }
                    if(f-i+1>=demanda.getNroFS()){//bloque que puede utilizarse
                        vector[0]=i;
                        vector[1]=f;
                        BloquesE.add(vector);
                        ban=1;
                    }
                }   
            }
            int aux[]=new int[2];
            for(int l=0;l<BloquesE.size();l++){  
                int cgbcopia=cgb;
                float sum=0, cfs=0;//contador  freciencia slots 
                aux=BloquesE.get(l);
                if  ((aux[1]-aux[0]+1)-demanda.getNroFS()==0){//al ubicarse la demanda si puede existir un bloque
                    cgbcopia=cgb-1;
                }
                for(int j=0;j<OE.length-1;j++){
                    if( (   (j+1)<aux[0] || j>(aux[0]+demanda.getNroFS()-1) && OE[j]==1) ){// && OE[j]==0 && OE[j+1]==0){
                        sum+=OE[j]*OE[j+1];
                        cfs++;
                    }
                }
                if(OE[OE.length-1]==1){
                       cfs ++;
                    }
                cp=(sum/cgbcopia)*(cfs/OE.length);  
                if(cp>mayorcp){
                    mayorcp=cp;
                    bloque1= aux[0];
                    bloque2=aux[0]+demanda.getNroFS()-1;
                }  
            }
            if(mayorcp>r.getCp()){//guardar bloque camino cp
                r.setCamino(k);
                r.setInicio(bloque1);
                r.setFin(bloque2);
                r.setCp(mayorcp);
            }
            k++;
        }  
        if (ban==1)
            return r;
        else 
            return null;
    }
    public static Resultado MTLSC_Algorithm(GrafoMatriz G, Demanda demanda,ListaEnlazada [] ksp, int capacidad){
        int k=0;
        int ban=0;
        float cl;
        int OE[]=new int [(capacidad)];
        
        int bloque1=0,bloque2=0;
        Resultado r=new Resultado();
        //ListaEnlazada ksp[]=new ListaEnlazada[3];
        
        //comienzo
        while (ksp.length>k && ksp[k]!=null){
            for(int w=0;w<(capacidad);w++){
                OE[w]=1;
            }
            Nodo t=ksp[k].getInicio();
            int Total=(G.acceder(t.getDato(),t.getSiguiente().getDato())).getFS().length;
            float mayorcl=0;
            for(int j=0;j<Total;j++){
                for(t=ksp[k].getInicio();t.getSiguiente().getSiguiente()!=null;t=t.getSiguiente()){
                    if(G.acceder(t.getDato(),t.getSiguiente().getDato()).getFS()[j].getEstado()==0){
                       OE[j]=0;
                       break;
                    } 
                }
            }
            int vector[]=new int[2];
            List<int[]> BloquesE = new LinkedList<int[]>();
            int cgb=0;//contador global de bloques 
            for (int c=0;c<OE.length;c++){//de cuanto y cuantos bloques espectrales libres hay
                int i=0,f=0;
                if(OE[c]==1){
                    cgb++;
                    i=f=c;
                    for(;c<OE.length;c++){
                        if(OE[c]==0 || c==OE.length-1){
                            f=c;
                        }
                    }
                    if(f-i+1>=demanda.getNroFS()){//bloque que puede utilizarse
                        vector[0]=i;
                        vector[1]=f;
                        BloquesE.add(vector);
                        ban=1;
                    }
                }   
            }
            int aux[]=new int[2];
            t=ksp[k].getInicio();
            FrecuencySlots enlace[]=new FrecuencySlots[G.acceder(t.getDato(),t.getSiguiente().getDato()).getFS().length];
            for(int l=0;l<BloquesE.size();l++){
                int cgbcopia=cgb;
                
                float sum=0, cfs=0;
                cl=0;
                for( ;t.getSiguiente().getSiguiente()!=null;t=t.getSiguiente()){
                    mayorcl=0;
                    enlace= G.acceder(t.getDato(),t.getSiguiente().getDato()).getFS();
                    //contador  freciency slots 
                    aux=BloquesE.get(l);
                    if  ((aux[1]-aux[0]+1)-demanda.getNroFS()==0){//al ubicarse la demanda si puede existir un bloque
                        cgbcopia=cgb-1;
                    }
                    for(int j=0;j<enlace.length-1;j++){
                        if( ( (  (j+1)<aux[0] || j>(aux[0]+demanda.getNroFS()-1) )) && enlace[j].getEstado()==1){// && OE[j]==0 && OE[j+1]==0){
                            sum+=enlace[j].getEstado()*enlace[j+1].getEstado();
                            cfs++;
                        }
                    }
                    if(enlace[enlace.length-1].getEstado()==1){
                       cfs ++;
                    }
                    cl=(sum/cgbcopia)*(cfs/OE.length);  
                    if(cl>mayorcl){
                        mayorcl=cl;
                        bloque1= aux[0];
                        bloque2=aux[0]+demanda.getNroFS()-1;
                    } 
                }
                   
            }
            
            if(mayorcl>r.getCp()){//guardar bloque camino cp
                r.setCamino(k);
                r.setInicio(bloque1);
                r.setFin(bloque2);
                r.setCp(mayorcl);
            }
            k++;
        }  
        if (ban==1)
            return r;
        else 
            return null;
    }
    /*
    * Algoritmo KSP-Fist Fit, utilizando para el ruteo los k camino mas cortos desde el nodo origen al final.
    * 
    * Paramentros:
    *   GrafoMatriz G: Topologia representada en forma de un grafo(Matriz de adjacencia).
    *   Demanda demanda: Solicitud con un otigen o, destino d y n FSs solicitados.
    *   ListaEnlazada [] ksp: Lista enlazada con los k caminos mas cortos.
    *   int capacidad: capacidad de cada enlaca en la topologia.
    * Retorna:
    *   Resultado: Es una estructura que representa el indice i origen, y j destino utilizados del espectro.
                 Retorna null en caso de que no exista espectro disponible.
    */
    public static Resultado KSP_FF_Algorithm(GrafoMatriz G, Demanda demanda,ListaEnlazada [] ksp,int capacidad){
        
        /*Definicion de variables las variables*/
        int inicio=0, fin=0,cont; // posicion inicial y final dentro del espectro asi como el contador de FSs contiguos disponibles
        int demandaColocada=0; // bandera para controlar si ya se encontro espectro disponible para la demanda.
        int [] OE= new int[capacidad]; //Ocupacion de Espectro.
        //Inicializadomos el espectro, inicialmente todos los FSs estan libres
        
        /*Probamos para cada camino, si exite espectro para ubicar la damanda*/
        int k=0;
        while(k<ksp.length && ksp[k]!=null && demandaColocada==0){
            for(int i=0;i<capacidad;i++){
                OE[i]=1;
            }
            /*Calcular la ocupacion del espectro para cada camino k*/
            for(int i=0;i<capacidad;i++){
                for(Nodo n=ksp[k].getInicio();n.getSiguiente().getSiguiente()!=null;n=n.getSiguiente()){
                    if(G.acceder(n.getDato(),n.getSiguiente().getDato()).getFS()[i].getEstado()==0){
                        OE[i]=0;
                        break;
                    }
                }
            }
            /*Teniendo la ocupacion del espectro del camino k, buscamos un bloque continuo de FS
            * que satisfazca la demanda.
            */
            inicio=fin=cont=0;
            for(int i=0;i<capacidad;i++){
                if(OE[i]==1){
                    inicio=i;
                    for(int j=inicio;j<capacidad;j++){
                        if(OE[j]==1){
                            cont++;
                        }
                        else{
                            cont=0;
                            break;
                        }
                        //si se encontro un bloque valido, salimos de todos los bloques
                        if(cont==demanda.getNroFS()){
                            fin=j;
                            demandaColocada=1;
                            break;
                        }
                    }
                }
                if(demandaColocada==1){
                        break;
                }
            }
            k++;
        }
        
        if(demandaColocada==0){
                return null; // Si no se encontro, en ningun camino un bloque contiguo de FSs, retorna null.
        }
        /*Bloque contiguoo encontrado, asignamos los indices del espectro a utilizar 
        * y retornamos el resultado
        */
        Resultado r= new Resultado();
        r.setCamino(k-1);
        r.setFin(fin);
        r.setInicio(inicio);
        return r;
    }
    
    /*
    * Algoritmo Random Fit, utilizando para el ruteo los k camino mas cortos desde el nodo origen al final.
    * Paramentros:
    *   GrafoMatriz G: Topologia representada en forma de un grafo(Matriz de adjacencia).
    *   Demanda demanda: Solicitud con un otigen o, destino d y n FSs solicitados.
    *   ListaEnlazada [] ksp: Lista enlazada con los k caminos mas cortos.
    *   int capacidad: capacidad de cada enlaca en la topologia.
    * Retorna:
    *   Resultado: Es una estructura que representa el indice i origen, y j destino utilizados del espectro.
                 Retorna null en caso de que no exista espectro disponible.
    */
    public static Resultado KSP_RF_Algorithm(GrafoMatriz G, Demanda demanda,ListaEnlazada [] ksp,int capacidad){
        
        /*Definicion de variables las variables*/
        int inicio=0, fin=0,cont; // posicion inicial y final dentro del espectro asi como el contador de FSs contiguos disponibles
        int demandaColocada=0; // bandera para controlar si ya se encontro espectro disponible para la demanda.
        int [] OE= new int[capacidad]; //Ocupacion de Espectro.
        int [] auxiliar= new int[capacidad];
        //Inicializadomos el espectro, inicialmente todos los FSs estan libres
        
        /*Probamos para cada camino, si exite espectro para ubicar la damanda*/
        int k=0;
        while(k<ksp.length && ksp[k]!=null && demandaColocada==0){
            for(int i=0;i<capacidad;i++){
                OE[i]=1;
            }
            /*Calcular la ocupacion del espectro para cada camino k*/
            for(int i=0;i<capacidad;i++){
                for(Nodo n=ksp[k].getInicio();n.getSiguiente().getSiguiente()!=null;n=n.getSiguiente()){
                    if(G.acceder(n.getDato(),n.getSiguiente().getDato()).getFS()[i].getEstado()==0){
                        OE[i]=0;
                        break;
                    }
                }
            }
            /*Teniendo la ocupacion del espectro del camino k, buscamos un bloque continuo de FS
            * que satisfazca la demanda.
            * La busqueda se realiza de forma aleatoria, probando desde cada indice del espectro,
            * si tenemos un bloque de FSs que satisfaga la demanda.
            */
            auxiliar=Utilitarios.listaDeNumeros(capacidad);
            inicio=fin=cont=0;
            int n=0;
            int i=0;
            while(i<capacidad && demandaColocada==0){
                inicio=auxiliar[i];
                for(int j=inicio;j<capacidad;j++){
                    if(OE[j]==1){
                        cont++;
                    }
                    else{
                        cont=0;
                        break;
                    }
                    //si se encontro un bloque valido, salimos de todos los bloques
                    if(cont==demanda.getNroFS()){
                        fin=j;
                        demandaColocada=1;
                        break;
                    }
                }
                i++;
            }
            k++;
        }
        if(demandaColocada==0){
                return null; // Si no se encontro, en ningun camino un bloque contiguo de FSs, retorna null.
        }
        /*Bloque contiguoo encontrado, asignamos los indices del espectro a utilizar 
        * y retornamos el resultado
        */
        Resultado r= new Resultado();
        r.setCamino(k-1);
        r.setFin(fin);
        r.setInicio(inicio);
        return r;
    }
    /*
    * Algoritmo Last Fit, utilizando para el ruteo los k camino mas cortos desde el nodo origen al final.
    * Paramentros:
    *   GrafoMatriz G: Topologia representada en forma de un grafo(Matriz de adjacencia).
    *   Demanda demanda: Solicitud con un otigen o, destino d y n FSs solicitados.
    *   ListaEnlazada [] ksp: Lista enlazada con los k caminos mas cortos.
    *   int capacidad: capacidad de cada enlaca en la topologia.
    * Retorna:
    *   Resultado: Es una estructura que representa el indice i origen, y j destino utilizados del espectro.
                 Retorna null en caso de que no exista espectro disponible.
    */
    public static Resultado KSP_LF_Algorithm(GrafoMatriz G, Demanda demanda,ListaEnlazada [] ksp,int capacidad){
        
        /*Definicion de variables las variables*/
        int inicio=0, fin=0,cont; // posicion inicial y final dentro del espectro asi como el contador de FSs contiguos disponibles
        int demandaColocada=0; // bandera para controlar si ya se encontro espectro disponible para la demanda.
        int [] OE= new int[capacidad]; //Ocupacion de Espectro.
        //Inicializadomos el espectro, inicialmente todos los FSs estan libres
        
        /*Probamos para cada camino, si exite espectro para ubicar la damanda*/
        int k=0;
        while(k<ksp.length && ksp[k]!=null && demandaColocada==0){
            for(int i=0;i<capacidad;i++){
                OE[i]=1;
            }
            /*Calcular la ocupacion del espectro para cada camino k*/
            for(int i=0;i<capacidad;i++){
                for(Nodo n=ksp[k].getInicio();n.getSiguiente().getSiguiente()!=null;n=n.getSiguiente()){
                    if(G.acceder(n.getDato(),n.getSiguiente().getDato()).getFS()[i].getEstado()==0){
                        OE[i]=0;
                        break;
                    }
                }
            }
            /*Teniendo la ocupacion del espectro del camino k, buscamos un bloque continuo de FS
            * que satisfazca la demanda desde la ultimas posiciones del espectro.
            */
            inicio=fin=cont=0;
            for(int i=capacidad-1;i>0;i--){
                if(OE[i]==1){
                    fin=i;
                    for(int j=fin;j>0;j--){
                        if(OE[j]==1){
                            cont++;
                        }
                        else{
                            cont=0;
                            break;
                        }
                        //si se encontro un bloque valido, salimos de todos los bloques
                        if(cont==demanda.getNroFS()){
                            inicio=j;
                            demandaColocada=1;
                            break;
                        }
                    }
                }
                if(demandaColocada==1){
                        break;
                }
            }
            k++;
        }
        
        if(demandaColocada==0){
                return null; // Si no se encontro, en ningun camino un bloque contiguo de FSs, retorna null.
        }
        /*Bloque contiguoo encontrado, asignamos los indices del espectro a utilizar 
        * y retornamos el resultado
        */
        Resultado r= new Resultado();
        r.setCamino(k-1);
        r.setFin(fin);
        r.setInicio(inicio);
        return r;
    }
    /*
    * Algoritmo FAR-Exact Fit, utilizando para el ruteo los k camino mas cortos desde el nodo origen al final.
    * Paramentros:
    *   GrafoMatriz G: Topologia representada en forma de un grafo(Matriz de adjacencia).
    *   Demanda demanda: Solicitud con un otigen o, destino d y n FSs solicitados.
    *   ListaEnlazada [] ksp: Lista enlazada con los k caminos mas cortos.
    *   int capacidad: capacidad de cada enlaca en la topologia.
    * Retorna:
    *   Resultado: Es una estructura que representa el indice i origen, y j destino utilizados del espectro.
                 Retorna null en caso de que no exista espectro disponible.
    */
    public static Resultado FAR_EF_Algorithm(GrafoMatriz G, Demanda demanda,ListaEnlazada [] ksp,int capacidad){
        
        /*Definicion de variables las variables*/
        int inicio=0, fin=0,cont, inicio2=0, fin2=0; // posicion inicial y final dentro del espectro asi como el contador de FSs contiguos disponibles
        int demandaExact=0,demandaColocada=0; // banderas para controlar si ya se encontro espectro disponible para la demanda.
        int [] OE= new int[capacidad]; //Ocupacion de Espectro.
        //Inicializadomos el espectro, inicialmente todos los FSs estan libres
        for(int i=0;i<capacidad;i++){
            OE[i]=1;
        }
        /*Probamos para cada camino, si exite espectro para ubicar la damanda*/
        int k=0;
        while(k<ksp.length && ksp[k]!=null && demandaExact==0){
            for(int i=0;i<capacidad;i++){
                OE[i]=1;
            }
            /*Calcular la ocupacion del espectro para cada camino k*/
            for(int i=0;i<capacidad;i++){
                for(Nodo n=ksp[k].getInicio();n.getSiguiente().getSiguiente()!=null;n=n.getSiguiente()){
                    if(G.acceder(n.getDato(),n.getSiguiente().getDato()).getFS()[i].getEstado()==0){
                        OE[i]=0;
                        break;
                    }
                }
            }
            /*Teniendo la ocupacion del espectro del camino k, buscamos un bloque continuo de FS
            * que satisfazca exactamente la demanda.
            */
            inicio=fin=cont=0;
            if(demandaColocada==0){
                inicio2=fin2=0;
            }
            for(int i=0;i<capacidad;i++){
                if(OE[i]==1){
                    inicio=i;
                    //Si aun no se encontro ningun bloque de FS que cumple,
                    // guadamos tambien en el inicio auxiliar.
                    if(demandaColocada==0){
                        inicio2=i;
                    }
                    for(int j=inicio;j<capacidad;j++){
                        if(OE[j]==1){
                            cont++;
                        }
                        else{
                            cont=0;
                            break;
                        }
                        //si se encontro un bloque valido, qeu satisface exactamente la demanda, salimos de todos los bucles
                        if(cont==demanda.getNroFS() && i+cont+1<capacidad && OE[i+cont +1]==0){
                            fin=j;
                            demandaExact=1;
                            break;
                        }
                        //Guardamos el fin auxiliar del primer bloque de FS encontrado que cumple,
                        // para el caso que no se encuentre uno que exactamente cumpla la demanda
                        if(cont==demanda.getNroFS() && demandaColocada==0){
                            fin2=j;
                            demandaColocada=1;
                        }
                    }
                }
                if(demandaExact==1){
                        break;
                }
            }
            k++;
        }
        
        if(demandaColocada==0 && demandaExact==0){
                return null; // Si no se encontro, en ningun camino un bloque contiguo de FSs, retorna null.
        }
        
        /*Bloque contiguoo exacto encontrado, asignamos los indices del espectro a utilizar 
        * y retornamos el resultado
        */
        Resultado r= new Resultado();
        if(demandaExact==1){
            r.setCamino(k-1);
            r.setFin(fin);
            r.setInicio(inicio);
            return r;
        }
        /*
        *No se encontro bloque contiguo exacto, por lo tanto, utilizamos First Fit
        */
        r.setCamino(k-1);
        r.setFin(fin2);
        r.setInicio(inicio2);
        return r;
        
    }
    
     /*
    * Algoritmo KSP-Least Used, utilizando para el ruteo los k camino mas cortos desde el nodo origen al final.
    * 
    * Paramentros:
    *   GrafoMatriz G: Topologia representada en forma de un grafo(Matriz de adjacencia).
    *   Demanda demanda: Solicitud con un otigen o, destino d y n FSs solicitados.
    *   ListaEnlazada [] ksp: Lista enlazada con los k caminos mas cortos.
    *   int capacidad: capacidad de cada enlaca en la topologia.
    * Retorna:
    *   Resultado: Es una estructura que representa el indice i origen, y j destino utilizados del espectro.
                 Retorna null en caso de que no exista espectro disponible.
    */
    public static Resultado KSP_LU_Algorithm(GrafoMatriz G, Demanda demanda,ListaEnlazada [] ksp,int capacidad){
        
        /*Definicion de variables las variables*/
        int inicio=0, fin=0,cont; // posicion inicial y final dentro del espectro asi como el contador de FSs contiguos disponibles
        int demandaColocada=0; // bandera para controlar si ya se encontro espectro disponible para la demanda.
        int bloqueEncontrado=0; // bandera para verificar si ya se encontro un bloqeu de FS libre.
        int [] OE= new int[capacidad]; //Ocupacion de Espectro.
        int [] util= new int[capacidad]; //utilizacion de los FSs.
        int [] bloque=new int[2]; // almacena la informacion de los indices inicio y fin del bloqeu con FSs libres
        int [] bloqueAux= new int [2]; // bloque auxiliar
        List bloques = new LinkedList(); // lista de bloques de FSs libres
        int pos_menor=0; //posicion del bloque menor en la lista de bloques
        //Inicializadomos el espectro, inicialmente todos los FSs estan libres
        
        /*Probamos para cada camino, si exite espectro para ubicar la damanda*/
        int k=0;
        while(k<ksp.length && ksp[k]!=null && demandaColocada==0){
            for(int i=0;i<capacidad;i++){
                OE[i]=1;
            }
            /*Calcular la ocupacion del espectro para cada camino k*/
            /*Calcular la utilizacion de los FSs en el espectro*/
            for(int i=0;i<capacidad;i++){
                int mayor=0;
                for(Nodo n=ksp[k].getInicio();n.getSiguiente().getSiguiente()!=null;n=n.getSiguiente()){
                    if(G.acceder(n.getDato(),n.getSiguiente().getDato()).getFS()[i].getEstado()==0){
                        OE[i]=0;
                    }
                    if(mayor<G.acceder(n.getDato(),n.getSiguiente().getDato()).getUtilizacion()[i]){
                        mayor=G.acceder(n.getDato(),n.getSiguiente().getDato()).getUtilizacion()[i];
                    }
                }
                util[i]=mayor;
                //System.out.println("mayor "+i+" = "+mayor);
            }
            /*
            *Obtener los bloques libres en el espectro
            */
            inicio=0;
            fin=0;
            for(int i=0;i<capacidad;i++){
                cont=0;
                int j=i;
                for(;j<capacidad;j++){
                    if(OE[j]==1){
                        cont++;
                    }
                    else{
                        break;
                    }
                }
                // si se encontro un bloque de FSs libre que cumplen con la demanda
                // vemos hasta donde llega el bloque
                if(cont>=demanda.getNroFS()){
                   demandaColocada=1;
                   inicio=i;
                   if(j<capacidad){
                       fin=j;
                   }else{
                       fin=capacidad-1;
                   }
                   bloque[0]=inicio;
                   bloque[1]=fin;
                   bloques.add(bloque);
                }
                i+=cont;
            }
            /*Ya se tiene todos los bloques libres del espectro*/
            if(demandaColocada==1){
                /*
                *Realizar los calculos de la utilziacion de cada bloque
                */
                //calculamos la utilizacion para la variable pivot(menor)
                // para despues, apartir de este, obtener el menor.
                int menor=0;
                bloqueAux=(int [])bloques.get(0);
                for(int j=bloqueAux[0];j<=bloqueAux[1];j++){
                    menor+=util[j];
                }
                int acumular=0;
                
                for(int i=1;i<bloques.size();i++){
                    bloqueAux=(int [])bloques.get(i);
                    acumular=0;
                    for(int j=bloqueAux[0];j<=bloqueAux[1];j++){
                        acumular+=util[j];
                    }
                    if(menor>acumular){
                        menor=acumular;
                        pos_menor=i;
                    }
                   //System.out.println("Pos menor = "+pos_menor);
                }
                /*Ya se sabe sabe cual es el bloque a asignar, esta marcado por el pos_menor
                 *Bloque contiguoo encontrado, asignamos los indices del espectro a utilizar 
                 * y retornamos el resultado
                 */
                //System.out.println("Pos menor = "+pos_menor);
                 bloque=(int [])bloques.get(pos_menor);
                  Resultado r= new Resultado();
                  r.setCamino(k);
                  r.setFin(bloque[1]);
                  r.setInicio(bloque[0]);
                  return r;
            }
            k++;
       }
       return null;
    }
    
     /*
    * Algoritmo KSP-Most Used, utilizando para el ruteo los k camino mas cortos desde el nodo origen al final.
    * 
    * Paramentros:
    *   GrafoMatriz G: Topologia representada en forma de un grafo(Matriz de adjacencia).
    *   Demanda demanda: Solicitud con un otigen o, destino d y n FSs solicitados.
    *   ListaEnlazada [] ksp: Lista enlazada con los k caminos mas cortos.
    *   int capacidad: capacidad de cada enlaca en la topologia.
    * Retorna:
    *   Resultado: Es una estructura que representa el indice i origen, y j destino utilizados del espectro.
                 Retorna null en caso de que no exista espectro disponible.
    */
    public static Resultado KSP_MU_Algorithm(GrafoMatriz G, Demanda demanda,ListaEnlazada [] ksp,int capacidad){
        
        /*Definicion de variables las variables*/
        int inicio=0, fin=0,cont; // posicion inicial y final dentro del espectro asi como el contador de FSs contiguos disponibles
        int demandaColocada=0; // bandera para controlar si ya se encontro espectro disponible para la demanda.
        int bloqueEncontrado=0; // bandera para verificar si ya se encontro un bloqeu de FS libre.
        int [] OE= new int[capacidad]; //Ocupacion de Espectro.
        int [] util= new int[capacidad]; //utilizacion de los FSs.
        int [] bloque=new int[2]; // almacena la informacion de los indices inicio y fin del bloqeu con FSs libres
        int [] bloqueAux= new int [2]; // bloque auxiliar
        List bloques = new LinkedList(); // lista de bloques de FSs libres
        int pos_mayor=0; //posicion del bloque menor en la lista de bloques
        //Inicializadomos el espectro, inicialmente todos los FSs estan libres
        
        /*Probamos para cada camino, si exite espectro para ubicar la damanda*/
        int k=0;
        while(k<ksp.length && ksp[k]!=null && demandaColocada==0){
            for(int i=0;i<capacidad;i++){
                OE[i]=1;
            }
            /*Calcular la ocupacion del espectro para cada camino k*/
            /*Calcular la utilizacion de los FSs en el espectro*/
            for(int i=0;i<capacidad;i++){
                int mayor=0;
                for(Nodo n=ksp[k].getInicio();n.getSiguiente().getSiguiente()!=null;n=n.getSiguiente()){
                    if(G.acceder(n.getDato(),n.getSiguiente().getDato()).getFS()[i].getEstado()==0){
                        OE[i]=0;
                    }
                    if(mayor<G.acceder(n.getDato(),n.getSiguiente().getDato()).getUtilizacion()[i]){
                        mayor=G.acceder(n.getDato(),n.getSiguiente().getDato()).getUtilizacion()[i];
                    }
                }
                util[i]=mayor;
                //System.out.println("mayor "+i+" = "+mayor);
            }
            /*
            *Obtener los bloques libres en el espectro
            */
            inicio=0;
            fin=0;
            for(int i=0;i<capacidad;i++){
                cont=0;
                int j=i;
                for(;j<capacidad;j++){
                    if(OE[j]==1){
                        cont++;
                    }
                    else{
                        break;
                    }
                }
                // si se encontro un bloque de FSs libre que cumplen con la demanda
                // vemos hasta donde llega el bloque
                if(cont>=demanda.getNroFS()){
                   demandaColocada=1;
                   inicio=i;
                   if(j<capacidad){
                       fin=j;
                   }else{
                       fin=capacidad-1;
                   }
                   bloque[0]=inicio;
                   bloque[1]=fin;
                   bloques.add(bloque);
                }
                i+=cont;
            }
            /*Ya se tiene todos los bloques libres del espectro*/
            if(demandaColocada==1){
                /*
                *Realizar los calculos de la utilziacion de cada bloque
                */
                //calculamos la utilizacion para la variable pivot(mayor)
                // para despues, apartir de este, obtener el menor.
                int mayor=0;
                bloqueAux=(int [])bloques.get(0);
                for(int j=bloqueAux[0];j<=bloqueAux[1];j++){
                    mayor+=util[j];
                }
                int acumular=0;
                
                for(int i=1;i<bloques.size();i++){
                    bloqueAux=(int [])bloques.get(i);
                    acumular=0;
                    for(int j=bloqueAux[0];j<=bloqueAux[1];j++){
                        acumular+=util[j];
                    }
                    if(mayor<acumular){
                        mayor=acumular;
                        pos_mayor=i;
                    }
                   //System.out.println("Pos menor = "+pos_menor);
                }
                /*Ya se sabe sabe cual es el bloque a asignar, esta marcado por el pos_menor
                 *Bloque contiguoo encontrado, asignamos los indices del espectro a utilizar 
                 * y retornamos el resultado
                 */
                //System.out.println("Pos menor = "+pos_menor);
                 bloque=(int [])bloques.get(pos_mayor);
                  Resultado r= new Resultado();
                  r.setCamino(k);
                  r.setFin(bloque[1]);
                  r.setInicio(bloque[0]);
                  return r;
            }
            k++;
       }
       return null;
    }
        /*
    * Algoritmo KSP para asignar los FS a cualquier camino, utilizando para el ruteo los k camino mas cortos desde el nodo origen al final.
    * 
    * Paramentros:
    *   GrafoMatriz G: Topologia representada en forma de un grafo(Matriz de adjacencia).
    *   Demanda demanda: Solicitud con un otigen o, destino d y n FSs solicitados.
    *   ListaEnlazada [] ksp: Lista enlazada con los k caminos mas cortos.
    *   int capacidad: capacidad de cada enlaca en la topologia.
    * Retorna:
    *   Resultado: Es una estructura que representa el indice i origen, y j destino utilizados del espectro.
                 Retorna null en caso de que no exista espectro disponible.
    */
    public static Resultado KSP_FF_Algorithm2_Greedy(GrafoMatriz G, Demanda demanda,ListaEnlazada [] ksp,int capacidad){
        
        /*Definicion de variables las variables*/
        int inicio=0, fin=0,cont; // posicion inicial y final dentro del espectro asi como el contador de FSs contiguos disponibles
        int demandaColocada=0; // bandera para controlar si ya se encontro espectro disponible para la demanda.
        int [] OE= new int[capacidad]; //Ocupacion de Espectro.
        //Inicializadomos el espectro, inicialmente todos los FSs estan libres
        
        
        Arrays.sort(ksp, new Comparator<ListaEnlazada>() {
                    public int compare(ListaEnlazada one, ListaEnlazada other) {
                        if(one.getTamanho()>other.getTamanho())
                            return 1;
                        if(one.getTamanho()<other.getTamanho())
                            return -1;
                        return 0;
                    }}); 
        
        
        
        /*ArrayList<Integer> vk = new ArrayList<Integer>();
        for (int i = 0; i < ksp.length; i++) {
            //System.out.println("se agrego el elmento:  "+i);
            vk.add(i, i);
        }
        Collections.shuffle(vk);
        ListaEnlazada [] aux=new ListaEnlazada[ksp.length];
        for (int i = 0; i < vk.size(); i++) {
            aux[vk.get(i)]=ksp[i];
        }
        ksp=aux;*/
        /*Probamos para cada camino, si exite espectro para ubicar la damanda*/
        
        int k=0;
        
        while(k<ksp.length && ksp[k]!=null && demandaColocada==0){
            for(int i=0;i<capacidad;i++){
                OE[i]=1;
            }
            /*Calcular la ocupacion del espectro para cada camino k*/
            for(int i=0;i<capacidad;i++){
                //System.out.println("falla en el indice::"+ccc);
                for(Nodo n=ksp[k].getInicio();n.getSiguiente().getSiguiente()!=null;n=n.getSiguiente()){
                   //System.out.println("v1 "+n.getDato()+" v2 "+n.getSiguiente().getDato()+" cant vertices "+G.getCantidadDeVertices()+" i "+i+" FSs "+G.acceder(n.getDato(),n.getSiguiente().getDato()).getFS().length);
                    if(G.acceder(n.getDato(),n.getSiguiente().getDato()).getFS()[i].getEstado()==0){
                        OE[i]=0;
                        break;
                    }
                }
            }
            /*Teniendo la ocupacion del espectro del camino k, buscamos un bloque continuo de FS
            * que satisfazca la demanda.
            */
            inicio=fin=cont=0;
            for(int i=0;i<capacidad;i++){
                if(OE[i]==1){
                    inicio=i;
                    for(int j=inicio;j<capacidad;j++){
                        if(OE[j]==1){
                            cont++;
                        }
                        else{
                            cont=0;
                            break;
                        }
                        //si se encontro un bloque valido, salimos de todos los bloques
                        if(cont==demanda.getNroFS()){
                            fin=j;
                            demandaColocada=1;
                            break;
                        }
                    }
                }
                if(demandaColocada==1){
                        break;
                }
            }
            k++;
        }
        
        if(demandaColocada==0){
                return null; // Si no se encontro, en ningun camino un bloque contiguo de FSs, retorna null.
        }
        /*Bloque contiguoo encontrado, asignamos los indices del espectro a utilizar 
        * y retornamos el resultado
        */
        Resultado r= new Resultado();
        r.setCamino(k-1);
        r.setFin(fin);
        r.setInicio(inicio);
        return r;
    }
    

    public static Resultado FAR_EF_Algorithm3(GrafoMatriz G, Demanda demanda,ListaEnlazada [] ksp,int capacidad){//greedy
        
        /*Definicion de variables las variables*/
        int inicio=0, fin=0,cont, inicio2=0, fin2=0; // posicion inicial y final dentro del espectro asi como el contador de FSs contiguos disponibles
        int demandaExact=0,demandaColocada=0; // banderas para controlar si ya se encontro espectro disponible para la demanda.
        int [] OE= new int[capacidad]; //Ocupacion de Espectro.
        //Inicializadomos el espectro, inicialmente todos los FSs estan libres
        
                
        //System.out.println("tamano lista ksp:  "+ksp.length);
        ArrayList<Integer> vk = new ArrayList<Integer>();
        for (int i = 0; i < ksp.length; i++) {
            //System.out.println("se agrego el elmento:  "+i);
            vk.add(i, i);
        }
        Collections.shuffle(vk);
        ListaEnlazada [] aux=new ListaEnlazada[ksp.length];
        for (int i = 0; i < vk.size(); i++) {
            aux[vk.get(i)]=ksp[i];
        }
        ksp=aux;
        

        
        /*Probamos para cada camino, si exite espectro para ubicar la damanda*/
        int k=0;
        while(k<ksp.length && ksp[k]!=null && demandaExact==0){
            for(int i=0;i<capacidad;i++){
                OE[i]=1;
            }
            /*Calcular la ocupacion del espectro para cada camino k*/
            for(int i=0;i<capacidad;i++){
                for(Nodo n=ksp[k].getInicio();n.getSiguiente().getSiguiente()!=null;n=n.getSiguiente()){
                    if(G.acceder(n.getDato(),n.getSiguiente().getDato()).getFS()[i].getEstado()==0){
                        OE[i]=0;
                        break;
                    }
                }
            }
            /*Teniendo la ocupacion del espectro del camino k, buscamos un bloque continuo de FS
            * que satisfazca exactamente la demanda.
            */
            inicio=fin=cont=0;
            if(demandaColocada==0){
                inicio2=fin2=0;
            }
            for(int i=0;i<capacidad;i++){
                if(OE[i]==1){
                    inicio=i;
                    //Si aun no se encontro ningun bloque de FS que cumple,
                    // guadamos tambien en el inicio auxiliar.
                    if(demandaColocada==0){
                        inicio2=i;
                    }
                    for(int j=inicio;j<capacidad;j++){
                        if(OE[j]==1){
                            cont++;
                        }
                        else{
                            cont=0;
                            break;
                        }
                        //si se encontro un bloque valido, qeu satisface exactamente la demanda, salimos de todos los bucles
                        if(cont==demanda.getNroFS() && i+cont+1<capacidad && OE[i+cont +1]==0){
                            fin=j;
                            demandaExact=1;
                            break;
                        }
                        //Guardamos el fin auxiliar del primer bloque de FS encontrado que cumple,
                        // para el caso que no se encuentre uno que exactamente cumpla la demanda
                        if(cont==demanda.getNroFS() && demandaColocada==0){
                            fin2=j;
                            demandaColocada=1;
                        }
                    }
                }
                if(demandaExact==1){
                        break;
                }
            }
            k++;
        }
        
        if(demandaColocada==0 && demandaExact==0){
                return null; // Si no se encontro, en ningun camino un bloque contiguo de FSs, retorna null.
        }
        
        /*Bloque contiguoo exacto encontrado, asignamos los indices del espectro a utilizar 
        * y retornamos el resultado
        */
        Resultado r= new Resultado();
        if(demandaExact==1){
            r.setCamino(k-1);
            r.setFin(fin);
            r.setInicio(inicio);
            return r;
        }
        /*
        *No se encontro bloque contiguo exacto, por lo tanto, utilizamos First Fit
        */
        r.setCamino(k-1);
        r.setFin(fin2);
        r.setInicio(inicio2);
        return r;
        
    }
    
}   
