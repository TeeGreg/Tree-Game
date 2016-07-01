package model.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe <code>Node</code> permettant de générer des noeuds que l'on
 * caractérisent par leur label ainsi que par une suite de successeurs.
 * 
 * V 1.2 : 
 * <ul>
 * 	<li>Implémentation supplémentaire lié à la méthode <code>Ulehla</code>.</li>
 * 	<li>Ajout de l'attribut <code>nimber</code> représentant le <code>nimber</code> du <code>Node</code>.</li>
 * 	<li>Ajout des méthodes <code>getNimber()</code> et <code>setNimber(int)</code> liées au nouvel attribut <code>nimber</code>.</li>
 * 	<li>Ajout de l'attribut <code>color</code> représentant la couleur du <code>Node</code>.</li>
 * 	<li>Ajout des méthodes <code>getColor()</code> et <code>setColor(boolean)</code> liées au nouvel attribut <code>color</code>.</li>
 * <ul> 
 * 
 * V 1.1 : 
 * <ul>
 * 	<li>Ajout de l'attribut <code>depth</code> indiquant la profondeur du <code>Node</code>.</li>
 * 	<li>Ajout des méthodes <code>getDepth()</code> et <code>setDeth(int)</code> liées au nouvel attribut <code>depth</code></li>
 * <ul> 
 * @author Nicolas
 * @date 15 nov. 2015
 * @version 1.2
 */
public class Node {
	
	/**
	 * Identifiant du noeud de type String.
	 */
	private String label;
	
	/**
	 * Liste de tout les successeurs du noeud courant.
	 */
	private List<Node> successors;
	
	/**
	 * Indique la profondeur du <code>Node</code> courant.
	 */
	private int depth;
	
	/**
	 * Représente la valeur du nimber dans le cadre de la méthode <code>Ulehla</code>.
	 * Par défaut, celui-ci est a 0, cela signifie que ce <code>Node</code> appartient au <code>noyau</code> d'un arbre.
	 */
	private int nimber;
	
	/**
	 * Constructeur de la classe <code>Node</code> prenant en paramètre un <code>label</code>.
	 * Ce constructeur peut être utilisé afin de crée des <code>Node</code>
	 * n'ayant pas de successeurs, car on préfère les lui ajouter après, 
	 * via une méthode.
	 * On initialise donc notre <code>List<Node></code> a vide.
	 * @param label Identifiant unique du <code>Node</code> courant.
	 * @since 1.0
	 * @version 1.2
	 */
	public Node(String label, int depth) {
		this.label = label;
		this.depth = depth;
		this.successors = new ArrayList<Node>();
		this.nimber = 0;
	}
	
	/**
	 * Constructeur de la classe <code>Node</code> prenant deux paramètres.
	 * Il prends une fois encore un <code>label</code> unique le représentant dans une forêt,
	 * ainsi qu'une liste de successeurs que l'on a crée au préalable.
	 * @param label Identifiant unique de <code>Node</code> courant.
	 * @param successors Successeurs du <code>Node</code> courant.
	 * @see model.tree.Node#Node(String, int)
	 * @since 1.0
	 * @version 1.0
	 */
	public Node(String label, int depth, List<Node> successors) {
		this(label, depth);
		this.successors = new ArrayList<Node>(successors);
	}
	
	/**
	 * Constructeur de la classe <code>Node</code> prenant X paramètres.
	 * Le premier est une fois encore un <code>label</codeQ> unique le représentant dans une forêt.
	 * Les autres sont une successions de <code>Node</code> que l'on passe les uns derrières les autres 
	 * sans avoir généré au préalable de List<Node>
	 * @param label Identifiant unique de <code>Node</code> courant.
	 * @param successors Successeurs du <code>Node</code> courant formant une succession de <code>Node</code> et non une <code>List<Node></code>.
	 * @see model.tree.Node#Node(String, int, List)
	 * @since 1.0
	 * @version 1.0
	 */
	public Node(String label, int depth, Node ... successors) {
        this(label, depth, Arrays.asList(successors));
    }
	
	/**
	 * Retourne le successeur de numéro <code>index</code>
	 * @param index Numéro du successeur a retourner.
	 * @return Le <code>Node</code> correspondant.
	 * @since 1.0
	 * @version 1.0
	 */
	public Node getSuccessor(int index) {
		return this.successors.get(index);
	}
	
	/**
	 * Retourne le numéro du successeur passé en paramètre.
	 * @param successor successeur dont on veut recupérer le numéro.
	 * @return Le numéro du successeur ou -1 si il n'y est pas.
	 * @since 1.0
	 * @version 1.0
	 */
	public int getIndexOfSuccessor(Node successor) {
		return this.successors.indexOf(successor);
	}
	
	/**
	 * Retourne une copie de la liste de tout les successeurs du <code>Node</code> courant.
	 * @return Une copie des successeurs.
	 * @see model.tree.Node#innerTree()
	 * @since 1.0
	 * @version 1.0
	 */
	public List<Node> getSuccessors() {
		List<Node> copy = new ArrayList<Node>(this.successors.size());
		for (Node node : this.successors) {
			copy.add(node);
		}
		return copy;
	}
	
	/**
	 * Retourne le nombre de successeur du <code>Node</code> courant.
	 * @return Le nombre de successeurs.
	 */
	public int getSuccessorCount() {
		return this.successors.size();
	}
	
	/**
	 * Retourne la profondeur courant du <code>Node</code>
	 * @return la profondeur.
	 * @since 1.1
	 * @version 1.0 
	 */
	public int getDepth() {
		return this.depth;
	}
	
	/**
	 * Retourne la label sous forme textuel.
	 * @return Une chaine représentant le <code>Node</code>
	 * @see model.tree.Node#innerTree()
	 * @since 1.0
	 * @version 1.0
	 */
	public String getLabel() {
		return this.label;
	}
	
	/**
	 * Retourne la valeur du <code>Nimber</code> pour le <code>Node</code> courant.
	 * Voir <code>Ulehla</code> pour plus de renseignement.
	 * @return La valeur du <code>Nimber</code>
	 * @since 1.2
	 * @version 1.0
	 */
	public int getNimber() {
		return this.nimber;
	}

	/**
	 * Affichage du <code>label</code>. 
	 * Utilisable dans l'IHM.
	 * @return <code>Label</code> du <code>Node</code> courant.
	 * @see model.tree.Node#getLabel()
	 * @since 1.0
	 * @version 1.0
	 */
	public String toString() {
		return this.getLabel();
	}
	
	/**
	 * Retourne une représentation textuel du sous arbre dont notre <code>Node</code> courant est une racine.
	 * @return Une représentation textuelle du sous abres du <code>Node</code> courant.
	 * @see model.tree.Node#getLabel()
	 * @see model.tree.Node#getSuccessors()
	 * @since 1.0
	 * @version 1.0
	 */
	public String innerTree() {
		StringBuilder str = new StringBuilder();
		str.append(this.getLabel() + "(");
		for (Node node : this.getSuccessors()) {
			str.append(node.innerTree());
		}
		str.append(")");
		return str.toString();
	}
	
	/**
	 * Modifie la liste des successeurs une fois le <code>Node</code> crée.
	 * @param successors Liste des successeurs du <code>Node</code> courant.
	 * @since 1.0
	 * @version 1.0
	 */
	public void setSuccessors(List<Node> successors) {
		this.successors = successors;
	}
	
	public void addSuccessor(Node n){
		this.successors.add(n);
	}
	
	/**
	 * Modifie la profondeur du <code>Node</code>.
	 * Utilisable lors de la suppression de <code>Node</code> sur un arbre.
	 * @param depth Nouvelle profondeur attribuer au <code>Node</code>
	 * @since 1.1
	 * @version 1.0
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	/**
	 * Modifie la valeur du <code>nimber</code> du <code>Node</code> courant.
	 * Voir <code>Ulehla</code> pour plus de renseignement.
	 * @param nimber Nouvel valeur du <code>Nimber</code>
	 * @since 1.2
	 * @version 1.0
	 */
	public void setNimber(int nimber) {
		this.nimber = nimber;
	}

	public boolean equals(Object o) {
		return this.getLabel() == ((Node) o).getLabel();
	}
}
