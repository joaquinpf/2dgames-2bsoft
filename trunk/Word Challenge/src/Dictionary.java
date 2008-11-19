import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


/**
 *Clase representando el diccionario de la aplicación.
 *
 * @author Marcos Lede
 */

public class Dictionary {

    /**
     * Tamaño de la palabra más largo permitido.
     */
    private static final int LONGEST_WORD = 6;

    /**
     * Tamaño de la palabra más corto permitido.
     */
    private static final int SMALLEST_WORD = 3;

    /**
     * HashSet conteniendo todas las palabras existentes del diccionario.
     */
    private HashSet < String > words;

    /**
     * ArrayList conteniendo todas las palabras de seis letras de longitud.
     */
    private ArrayList < String > sixLetteredWord;

    /**
     * ArrayList conteniendo todas las palabras de seis letras de longitud
     * que aún no han sido utilizadas.
     */
    private ArrayList < String > unusedSixLetteredWord;


    /**
     * Constructor de la clase.
     * @param wordlist : palabras que acepta el diccionario
     */
    public Dictionary(final HashSet < String > wordlist) {
        initWordList(wordlist);
    }

    /**
     * Inserta las palabras que manejará el diccionario y actualiza las
     * estructuras.
     * @param wordlist : conjunto de palabras contenidas por el diccionario
     */
    private void initWordList(final HashSet < String > wordlist) {
        this.words = wordlist;
        sixLetteredWord = new ArrayList <String>();
        unusedSixLetteredWord = new ArrayList <String>();
        Iterator<String> iter = wordlist.iterator();
        while (iter.hasNext()) {
            String word;
            word = (String) iter.next();
            if (word.length() == LONGEST_WORD) {
                sixLetteredWord.add(word);
                unusedSixLetteredWord.add(word);
            }
        }
    }

    /**
     * Retorna el HashSet con todas las palabras existentes.
     * @return words : HashSet de palabras existentes
     */
    public final HashSet < String > getWords() {
        return words;
    }

    /**
     * Inserta un conjunto totalmente renovado de palabras al diccionario.
     * @param newWords : conjunto totalmente renovado de palabras
     */
    public final void setWords(final HashSet < String > newWords) {
        initWordList(newWords);
    }

    /**
     * Devuelve todas lsa palabras de seis letras.
     * @return sixLetteredWord : palabras de seis letras
     */
    public final ArrayList < String > getSixLetteredWord() {
        return sixLetteredWord;
    }

    /**
     * Devuelve las letras de una palabras de seis letras.
     * @return permutedWord : palabra de seis letras permutada
     */
    public final String getSixLetters() {
        Random random = new Random();
        ArrayList < Integer > indexes = new ArrayList<Integer>();

        //Si todas las palabras de seis letras ya fueron utilizadas,
        //se vuelven a reponer todas las existentes
        if (unusedSixLetteredWord.size() == 0) {
            unusedSixLetteredWord = sixLetteredWord;
        }

        //Escoge aleatoriamente una palabra y la elimina de la lista de
        //palabras no utilizadas
        int randomSercher = random.nextInt(unusedSixLetteredWord.size());
        String word = unusedSixLetteredWord.get(randomSercher);
        unusedSixLetteredWord.remove(randomSercher);

        //Inicializa un arreglo de enteros auxiliar para poder permutar
        //las letras de la palabra encontrada
        for (int i = 0; i < LONGEST_WORD; i++) {
            indexes.add(i);
        }

        //Concatena las letras de la palabra mezclándolas aleatoriamente
        String permutedWord = new String("");
        for (int i = 0; i < LONGEST_WORD; i++) {
            int pos = random.nextInt(indexes.size());
            int index = indexes.get(pos);
            indexes.remove(pos);
            permutedWord = new String(permutedWord
                + word.substring(index, index + 1));
        }
        return permutedWord;
    }

    /**
     * Método para verificar que los índices no sean iguales.
     * @param cont : arreglo de índices
     * @return true : si al menos dos de los índices son iguales
     */
    private boolean sameIndexes(final int[] cont) {
        for (int i = 0; i < cont.length; i++) {
            for (int j = 0; j < cont.length; j++) {
                if (i != j) {
                    if (cont[i] == cont[j]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Método para devolver todas las palabras existentes dado un conjunto
     * de letras.
     * @param letters : conjunto de letras
     * @param wordList : ArrayList donde se depositan las palabras encontradas
     */
    private void addExistentWords(final String letters,
                                  final ArrayList < String > wordList) {
        //Arreglo de índices auxiliar
        int[] indexPos = new int[letters.length()];
        for (int i = 0; i < indexPos.length; i++) {
            indexPos[i] = i;
        }

        if (words.contains(letters) && !wordList.contains(letters)) {
            wordList.add(letters);
        }

        //Ciclo que concluye cuando el primer índice excede en número total
        //de letras dado. Esto permite que se prueben todas las combinaciones
        //posibles
        while (indexPos[0] < letters.length()) {
            indexPos[letters.length() - 1]++;
            for (int i = letters.length() - 1; i > 0; i--) {
                if (indexPos[i] == letters.length()) {
                    indexPos[i] = 0;
                    indexPos[i - 1]++;
                }
            }
            //Si no se está utilizando la misma letra del conjunto de letras
            //dado en más de una ocasión, entonces se procede a evaluar la
            //existencia de la palabra
            if (!sameIndexes(indexPos)) {
                String aux = new String("");
                //Ciclo que forma una potencial palabra con las letras apuntadas
                //por el arreglo de índices
                for (int i = 0; i < letters.length(); i++) {
                    aux = new String(aux + letters.substring(indexPos[i],
                                                             indexPos[i] + 1));
                }
                if (words.contains(aux) && !wordList.contains(aux)) {
                    wordList.add(aux);
                }
            }
        }
    }

    /**
     * Método para seleccionar todos los subgrupos de letras para formar
     * palabras.
     * @param letters : conjunto de letras
     * @param sizeGroup : tamaño de los subconjuntos de letras
     * @param lettersGroup : ArrayList donde se depositan las subconjuntos
     * encontradas
     */
    private void selectLettersGroup(final String letters, final int sizeGroup,
                                    final ArrayList < String > lettersGroup) {
        //Arreglo de índices auxiliar
        int[] indexPos = new int[sizeGroup];
        for (int i = 0; i < indexPos.length; i++) {
            indexPos[i] = i;
        }

        lettersGroup.add(letters.substring(0, sizeGroup));
        int indexToMove = sizeGroup - 1;
        //Ciclo que concluye cuando se intenta mover un índice inexistente.
        //Esto ocurre cuando ningún índice puede moverse a derecha.
        while (indexToMove >= 0) {
            indexToMove = sizeGroup - 1;
            boolean selected = false;
            for (int i = sizeGroup - 1; i >= 0 && !selected; i--) {
                //Todos los índices pueden moverse a derecha hasta determinada
                //posición. Alcanzada esta posición tiene prioridad de
                //movimiento el índice inmediatamente anterior, y en caso de no
                //poder hacerlo, el anterior, y así sucesivamente. Cuando se
                //mueve el índice anterior el inmediatamente superior debe
                //colocarse una posición adelante, y la misma lógica para los
                //posterirores
                if (indexPos[i] < i + letters.length() - sizeGroup) {
                    indexToMove = i;
                    indexPos[indexToMove]++;
                    for (int j = i + 1; j < sizeGroup; j++) {
                        indexPos[j] = indexPos[j - 1] + 1;
                    }
                    selected = true;
                } else {
                    indexToMove--;
                }
            }
            String aux = new String("");
            //Ciclo que forma un subgrupo de letras para la posterior búsqueda
            //de palabras existentes
            for (int i = 0; i < sizeGroup; i++) {
                aux = new String(aux + letters.substring(indexPos[i],
                                                         indexPos[i] + 1));
            }
            if (!lettersGroup.contains(aux)) {
                lettersGroup.add(aux);
            }
        }
    }

    /**
     * Método para obtener todas palabras posibles dado un conjunto de letras.
     * @param letters : conjunto de letras
     * @return wordList : lista de palabras encontradas
     */
    public final ArrayList < String > getPossibleWords(final String letters) {
        ArrayList < String > lettersGroup = new ArrayList <String>();
        ArrayList < String > wordList = new ArrayList <String>();
        //Se sleccionan todos los posibles subgrupos de letras para
        //posteriormenete permutarlos y evaluar la existencia de palabras.
        //Los subgrupos se van agregando en el ArrayList 'lettersGroup'
        for (int i = SMALLEST_WORD; i <= letters.length(); i++) {
            selectLettersGroup(letters, i, lettersGroup);
        }

        //Dados todos los subgrupos de letras existentes se realizan todas las
        //permutaciones posibles, y en el caso de que alguna forme una palabra
        //existente se la agrega al ArrayList 'wordList'
        for (int i = 0; i < lettersGroup.size(); i++) {
            addExistentWords(lettersGroup.get(i), wordList);
        }

        return wordList;
    }


    /**
     * Método para agregar una palabra al diccionario.
     * @param newWord : palabra que se agrega al diccionario
     */
    public final void addWord(final String newWord) {
        if (!words.contains(newWord)) {
            words.add(newWord);
            if (newWord.length() == LONGEST_WORD) {
                sixLetteredWord.add(newWord);
                unusedSixLetteredWord.add(newWord);
            }
        }
    }

}
