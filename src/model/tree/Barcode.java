package model.tree;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe <code>Tree</code> permettant de générer un "code-barre" pour un arbre donnée. 		
 * 
 * V 1.1 : 
 * <ul>
 * 	<li>Ajout des méthodes <code>setCode()</code> et <code>generateTree()</code></li>
 * 	<li>Ajout de la classe interne <code>LinkedNode</code></li>
 * <ul> 
 * @author Grégoire
 * @date 15 nov. 2015
 * @version 1.1
 */
public class Barcode implements Comparable<Barcode> {
	
	/**
	 * Code d'identification du <code>Barecode</code> composé de 0 et de 1.
	 */
	private String code;
	
	/**
	 * Constructeur de la classe <code>Barcode</code> initialisant le <code>code</code> a "10".
	 * @since 1.0
	 * @version 1.0
	 */
	public Barcode() {
		this.code = "10";	
	}
	
	/**
	 * Constructeur de la classe <code>Barcode</code> prenant en paramètre un <code>code</code> déjà existant. 
	 * @param codes <code>Code</code> que l'on à déjà généré et que l'on passe afin de construire la suite du "code-barre".
	 * @see model.tree.Barcode#sort(Barcode[])
	 * @since 1.0
	 * @version 1.0
	 */
	public Barcode(Barcode[] codes) {
		this.code = "1" + this.sort(codes) + "0";
	}
	
	/**
	 * Retourne le <code>code</code> courant.
	 * @return Le code actuel composé de 0 et de 1.
	 * @since 1.0
	 * @version 1.0
	 */
	public String getCode() {
		return this.code;
	}
	
	/**
	 * Modifie le <code>code</code> courant.
	 * @param c Nouveau <code>code</code>.
	 * @since 1.1
	 * @version 1.0
	 */
	public void setCode(String c) {
		this.code = c;
	}
	
	/**
	 * Retourne la taille du <code>Barcode</code>
	 * @return La taille du tableau.
	 * @since 1.0
	 * @version 1.0
	 */
	public int getSize() {
		return code.length() / 2;
	}
	
	/**
	 * @param bc <code>Barcode</code> que l'on compare au <code>Barcode</code> courant.
	 * @return -1 / 0 / 1 suivant si les "code-barre" sont identique, ou supérieur (inférieur) l'un à l'autre.
	 * @see model.tree.Barcode#getCode()
	 * @see model.tree.Barcode#compareTo(Barcode)
	 * @since 1.0
	 * @version 1.0
	 */
	public int compareTo(Barcode bc) {
		if (bc.getCode().length() > this.code.length()) {
			return -1;
		}
		if (bc.getCode().length() < this.code.length()) {
			return 1;
		}
		return this.code.compareTo(bc.getCode());
	}
	
	/**
	 * Tri les <code>Barcode</code> afin de retourner un résultat composé de chque <code>Barcode</code> trié comme il faut.
	 * @param codes
	 * @return Le <code>Barcode</code> trié correctement.
	 * @see model.tree.Barcode#getCode()
	 * @since 1.0
	 * @version 1.0
	 */
	private String sort(Barcode[] codes) {
		Arrays.sort(codes);
		String result = "";
		for (Barcode bc : codes) {
			result += bc.getCode();
		}
		return result;
	}
	
	/**
	 * Méthode permettant de générer un <code>Tree</code> à partir de son <code>Barcode</code>. 
	 * @return L'arbre issue du <code>Barcode</code>.
	 * @see model.tree.Node#getSuccessors()
	 * @see model.tree.Node#setSuccessors(List)
	 * @see model.tree.Node#getDepth()
	 * @see model.tree.Tree#Tree(Node)
	 * @see model.tree.Barcode.LinkedNode#getPred()
	 * @since 1.1
	 * @version 1.0
	 */
	public Tree generateTree() {		
		LinkedNode n = new LinkedNode("0", 0, null);
		n.setPred(n);
		
		for(int label = 1; label < code.length(); label++) {
			if(code.charAt(label) == '1') {
				LinkedNode d = new LinkedNode("" + label, n.getDepth() + 1, n);
				List<Node> l = new ArrayList<Node>();
				l.addAll(n.getSuccessors());
				l.add(d);
				n.setSuccessors(l);
				n = d;
			} else {
				n = (LinkedNode) n.getPred();
			}
		}
		return new Tree(n);
	}
	
	/**
	 * Classe interne permettant de recréer un arbre a partir de son <code>Barcode</code>
	 * La classe <code>LinkedNode</code> extends <code>Node</cdoe> en y ajoutant un prédécesseur.
	 * 
	 * @author Grégoire
	 * @date 18 déc. 2015
	 * @version 1.0
	 */
	private class LinkedNode extends Node {
		
		/**
		 * Prédecesseur du <code>Node</code> courant. 
		 */
		private Node pred;
		
		/**
		 * Constructeur prenant le <code>label</code>, la profondeur <code>depth</code> ainsi que le prédecesseur <code>pred</code>.
		 * 
		 * @param label Identifiant du <code>Node</code>.
		 * @param depth Profondeur du <code>Node</code>.
		 * @param pred Prédecesseur du <code>Node</code>.
		 * @see model.tree.Node#Node(String, int)
		 */
		public LinkedNode(String label, int depth, Node pred) {
			super(label, depth);
			setPred(pred);	
		}
		
		/**
		 * Modificateur du predecesseur du <code>Node</code> courant.
		 * @param n Nouveau prédécesseur.
		 * since 1.0
		 * @version 1.0
		 */
		public void setPred(Node n) {
			pred = n;
		}
		
		/**
		 * Retourne le prédécesseur du <code>Node</code> courant.
		 * @return Le prédécesseur du <code>Node</code> courant.
		 * @since 1.0
		 * @version 1.0
		 */
		public Node getPred() {
			return pred;
		}
	}
}

