package model.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe <code>GhostTree</code> représentant un arbre possèdant un <code>Node</code> "fantôme".
 * Cela signifie que plutôt de générer une forêt d'arbre, nous préférons représenter les arbres 
 * sous forme d'un arbre possèdant une racine commune a tous les arbres.
 * @author Grégoire, Nicolas
 * @date 11 déc. 2015
 * @see model.tree.Tree
 * @version 1.0
 */
public class GhostTree extends Tree {
	
	/**
	 * Constructeur de la classe <code>GhostTree</code> représenant une forêt d'arbre possèdant une Racine "fantome".
	 * @param root Racine a partir de laquelle on rattache notre <code>Tree</code>.
	 * @see model.tree.GhostTree#createNode(Node)
	 * @since 1.0
	 * @version 1.0
	 */
	public GhostTree(Node root) {
		super(GhostTree.createNode(root));
		
	}

	/**
	 * Constructeur prenant en paramètre une <code>List<Node></code> représentant les racine des arbres
	 * auquel on souhaite adjoindre une racine commune.
	 * @param treeRoot Liste de <code>Node</code> permettant de placer une multitude d'arbre a notre racine.
	 * @see model.tree.GhostTree#createNode(List)
	 * @since 1.0
	 * @version 1.0
	 */
	public GhostTree(List<Node> treeRoot) {
		super(GhostTree.createNode(treeRoot));
	}
	
	/**
	 * Méthode permetant de supprimer les <code>Node</code> issues de notre <code>GhostTree</code>
	 * @param node <code>Node</code> à partir duquel on supprime nos <code>Node</code>.
	 * @return Un <code>Tree</code> auquel on a supprimer les <code>Node</code> suivant les règles du jeu.
	 * @see model.tree.Tree#delete(Node)
	 * @since 1.0
	 * @version 1.0
	 */
	public Tree DeleteGhostTree(Node node) {
		return new GhostTree(super.delete(node));
	}

	/**
	 * Méthode peremttant de parcourir notre <code>GhostTree</code> via parcours en profondeur 
	 * et de générer le tableau suffixe S de notre <code>GhostTree</code>.
	 * @param root Racine à partir de laquelle on effectue notre parcours.
	 * @return Une<code>List<Node></code> représentant le tableau S.
	 * @see model.tree.Tree#suffixeDepthCourse(Node)
	 * @since 1.0
	 * @version 1.0
	 */
	public List<Node> suffixeDepthCourseGhost(Node root) {
		List<Node> l = super.suffixeDepthCourse(root);
		l.remove(l.size());
		return l;
	}

	/**
	 * Méthode peremttant de parcourir notre <code>GhostTree</code> de générer le tableau préfixe P de notre <code>GhostTree</code>.
	 * @param root Racine à partir de laquelle on effectue notre parcours.
	 * @return Une<code>List<Node></code> représentant le tableau P.
	 * @see model.tree.Tree#prefixeDepthCourse(Node)
	 * @since 1.0
	 * @version 1.0
	 */
	public List<Node> prefixeDepthCourseGhost(Node root) {
		List<Node> l = super.prefixeDepthCourse(root);
		l.remove(0);
		return l;
	}
	
	/**
	 * Méthode <code>static</code> permettant d'initialiser nos constructeurs de la classe prenant en paramètre une sule <code>Node</code>. 
	 * @param n <code>Node</code> représentant la racine de l'arbre que l'on souhaite ajouter.
	 * @return La nouvelle racine de notre Arbre.
	 * @see model.tree.Node#setSuccessors(List)
	 * @since 1.0
	 * @version 1.0
	 */
	private static Node createNode(Node n) {
		Node node = new Node("0", 0);
		List<Node> l = new ArrayList<Node>();
		l.add(n);
		node.setSuccessors(l);
		return node;
	}	
	
	/**
	 * Méthode <code>static</code> permettant d'initialiser nos constructeurs de la classe prenant en paramètre une <code>List<Node></code>. 
	 * @param l <code>List<Node></code> représentant les racines des arbres que l'on souhaite ajouter a notre Arbre..
	 * @return La nouvelle racine de notre Arbre.
	 * @see model.tree.Node#setSuccessors(List)
	 * @since 1.0
	 * @version 1.0
	 */
	private static Node createNode(List<Node> l) {
		Node node = new Node("0", 0);
		node.setSuccessors(l);
		return node;
	}
}
