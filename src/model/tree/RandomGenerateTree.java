package model.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant de généré aléatoirement un arbre via diverses options que l'on pourra lire dans un fichier de configuration.
 * Elle hérite de Tree afin de pouvoir utlilisé nombres des méthodes nécéssaire a la fabrication d'un arbre.
 * V 1.2 : 
 * <ul>
 * 	<li>Ajout des méthodes setDepth(), getDepth(), setMaxNode(), getMaxNode(), ainsi que modification des la méthode de génération aléatoire de l'arbre.</li>
 * <ul> 
 * 	
 *  
 * V 1.1 : 
 * <ul>
 * 	<li>Ajout de la méthode generateLargeTree(Node) permettant de générer un arbre en largeur.</li>
 * 	<li>Déplacement de la méthode LargeCourses et DepthCourses dans <code>Tree</code>.</li>
 * 	<li>Déplacement de la méthode suffixeDepthCourse dans <code>Tree</code>.</li>
 * 	<li>Suppression du booléen static IS_BINARY, car inutile. En effet, il suffit de mettre MAX_NODE à 2.</li>
 * <ul> 
 * 	
 * @author Grégoire, Nicolas, Florian 
 * @date 15 nov. 2015
 * @see model.tree.Tree
 * @version 1.1
 */
public class RandomGenerateTree extends Tree {

	/**
	 * Valeur par défaut que l'on veut en terme de profondeur de notre Arbre.
	 */
	private static final int DEFAULT_DEPTH = 5;
	
	/**
	 * Valeur maximal d'enfant par <code>Node</code>.
	 */
	private static final int MAX_NODE = 3;
	
	/**
	 * Valeur pour la profondeur de l'arbre
	 */
	private int depth;
	
	/**
	 * Valeur pour le nombre maximum de noeuds dans l'arbre
	 */
	private int maxNode;
	
	/**
	 * Dernier <code>label</code> affecté a un <code>Node</code> 
	 */
	private String lastLabel;
	
	/**
	 * Constructeur prenant la root de notre Tree en paramètre.
	 * @param root Racine de notre arbre.
	 * @see model.tree.Node#getLabel()
	 * @see model.tree.RandomGenerateTree#generateLargeTree(Node)
	 * @since 1.0
	 * @version 1.0
	 */
	public RandomGenerateTree(Node root) {
		super(root);
		this.lastLabel = ((Node) super.getRoot()).getLabel();
		this.generateLargeTree(root);
	}
	
	/**
	 * Constructeur à trois paramètres.
	 * @param root Racine de l'arbre aléatoire.
	 * @param depth Profondeur maximum qu souhaite l'utilisateur.
	 * @param maxNode Nombre de <code>Node</code> max par <code>Node</code>.
	 * @see model.tree.RandomGenerateTree#setDepth(int)
	 * @see model.tree.RandomGenerateTree#setMaxNode(int)
	 * @see model.tree.RandomGenerateTree#generateLargeTree(Node)
	 * @since 1.2
	 * @version 1.0
	 */
	public RandomGenerateTree(Node root, int depth, int maxNode) {
		super(root);
		this.setDepth(depth);
		this.setMaxNode(maxNode);
		this.lastLabel = ((Node) super.getRoot()).getLabel();
		this.generateLargeTree(root);
	}
	
	/**
	 * Retourne le dernier <code>label</code> donnée a un <code>Node</code>
	 * @return Dernier <code>label</code> utilisé.
	 * @since 1.0
	 * @version 1.0
	 */
	public String getLastLabel() {
		return lastLabel;
	}
	
	/**
	 * Modifie le nom du dernier <code>label</code> donnée.
	 * @param lastLabel nouveau <code>label</code>.
	 * @since 1.0
	 * @version 1.0
	 */
	public void setLastLabel(String lastLabel) {
		this.lastLabel = lastLabel;
	}
	
	/**
	 * Retourne le nombre maximum de noeuds pour l'arbre.
	 * @return le nombre maximum de noeuds pour l'arbre.
	 * @since 1.1
	 * @version 1.0
	 */
	public int getMaxNode() {
		return this.maxNode;
	}
	
	/**
	 * Modifie le nombre maximum de noeuds pour l'arbre.
	 * @param n nombre de noeuds maximum.
	 * @since 1.2
	 * @version 1.0
	 */
	public void setMaxNode(int n) {
		if (n > MAX_NODE || n <= 0) {
			this.maxNode = MAX_NODE;
		}
		else {
			this.maxNode = n;
		}
	}
	
	/**
	 * Retourne la profondeur de l'arbre.
	 * @return profondeur de l'arbre.
	 * @since 1.1
	 * @version 1.0
	 */
	public int getDepth() {
		return this.depth;
	}
	
	/**
	 * Modifie la profondeur de l'arbre.
	 * @param d profondeur de l'arbre.
	 * @since 1.2
	 * @version 1.0
	 */
	public void setDepth(int d) {
		if (d > DEFAULT_DEPTH || d <= 0) {
			this.depth = DEFAULT_DEPTH;
		}
		else {
			this.depth = d;
		}
	}
	
	/**
	 * Permet de générer un arbre aléatoire.
	 * On choisi aleatoirement un nombre de <code>Node</code> via une constante.
	 * Ensuite, on parcours en profondeur notre arbre et on lui crée des enfants
	 * @see model.tree.Node#getDepth()
	 * @see model.tree.Tree#getRoot()
	 * @see model.tree.RandomGenerateTree#getMaxNode()
	 * @see model.tree.Tree#prefixeDepthCourse(Node)
	 * @see model.tree.RandomGenerateTree#generateNode(Node, int, int)
	 * @since 1.0
	 * @version 1.0
	 */
	public void generateTree() {
		int nbNode = (int)(Math.random() * (this.getMaxNode() + 1));
		
		if (nbNode > 0) {
			Node node = (Node) this.prefixeDepthCourse((Node) super.getRoot());
			if (node != null) {
				this.generateNode(node, nbNode, node.getDepth() + 1);
			}
		}
	}
	
	/**
	 * Permet de générer les successeurs du <code>Node</code> que l'on passe en paramètre.
	 * @param parent Racine de la parenté.
	 * @param nbSuccessor Nombre de successeur a créer.
	 * @param depth Profondeur des successeurs obtenus.
	 * @see model.tree.Node#getLabel()
	 * @see model.tree.Node#setSuccessors(List)
	 * @see model.tree.Node#Node(String, int, List)
	 * @see model.tree.RandomGenerateTree#setLastLabel(String)
	 * @see model.tree.RandomGenerateTree#getLastLabel()
	 * @since 1.0
	 * @version 1.0
	 */
	private void generateNode(Node parent, int nbSuccessor, int depth) {
		if (nbSuccessor > 0 && parent != null) {
			List<Node> successors = new ArrayList<Node>(nbSuccessor);
			Node node;
			
			for (int i = 1; i <= nbSuccessor; i++) {
				int value = Integer.parseInt(this.getLastLabel()) + 1;
				node = new Node(String.valueOf(value), depth);
				successors.add(node);
				this.setLastLabel(node.getLabel());
			}
			parent.setSuccessors(successors);
		}
	}

	/**
	 * Générateur de <code>Tree</code> aléatoire via parcours en largeur.
	 * @param root Racine de notre <code>Tree</code> courant.
	 * @return Un <code>Node</code> représentant la racine de notre <code>Tree</code>
	 * @see model.tree.Tree#largeCourse(List, int)
	 * @see model.tree.RandomGenerateTree#generateNode(Node, int, int)
	 * @see model.tree.RandomGenerateTree#getDepth()
	 * @see model.tree.RandomGenerateTree#getMaxNode()
	 * @since 1.1
	 * @version 1.0
	 */
	private Node generateLargeTree(Node root) {
		for (int i = 0; i < this.getDepth(); i++) {
			List<Node> al = new ArrayList<Node>();
			al.add(root);
			al = super.largeCourse(al, i);
			for (Node n : al) {
				this.generateNode(n, (int)(Math.random() * (this.getMaxNode() + 1)), i);
			}
		}
		return root;
	}
}