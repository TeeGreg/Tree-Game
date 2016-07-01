package model.ai;

import java.util.ArrayList;
import java.util.List;

import model.tree.Node;
import model.tree.Tree;

/**
 * Constructeur de la classe <code>AI</code>, repérsentant les <code>Intelligence Artificielle</code> codées pour le projet.
 * Elle prends plusieurs paramètres necessaire afin de faire tourner les <code>AI</code> qui sont :
 * <ul>
 * 	<li>La méthode <code>Exaustive</code>, fonctionnant sur le principe suivant : Tester tout les coups possibles et déterminer le gagnant.</li>
 * 	<li>La méthode <code>Ulehla</code>, fonctionnant via un algortihme de recherche du coup gagnant très rapide.</li>
 * </ul>
 * @author Grégoire
 * @date 11 déc. 2015
 * @version 1.0
 */
public class AI {
	
	/**
	 * <code>Tree</code> sur lequel on effectue les deux <code>AI</code>.
	 */
	private Tree tree;
	
	/**
	 * <code>List<Node></code> représentant les coups gagnants.
	 */
	private List<Node> winninghits;
	
	/**
	 * Constructeur de la classe <code>AI</code> prenant l'arbre sur lequel on souhaite 
	 * executé le code de nos <code>AI</code>.
	 * @param t <code>Tree</code> sur lequel on effectue nos algortihme d'IA.
	 * @since 1.0
	 * @version 1.0
	 */
	public AI(Tree t) {
		this.setTree(t);
		this.setWinninghits(new ArrayList<Node>());
	}

	/**
	 * Retourne le <code>Tree</code> avec lequel on joue.
	 * @return Le <code>Tree</code> sur lequel on joue.
	 * @since 1.0
	 * @version 1.0
	 */
	public Tree getTree() {
		return tree;
	}

	/**
	 * Modificateur du <code>Tree</code> sur lequel on joue.
	 * @param tree Nouveau <code>Tree</code> sur lequel on souhaite jouer.
	 * @since 1.0
	 * @version 1.0
	 */
	public void setTree(Tree tree) {
		this.tree = tree;
	}

	/**
	 * Retourne la <code>List<Node></code> représenant les coups gagants. 
	 * @since 1.0
	 * @version 1.0
	 */
	public List<Node> getWinninghits() {
		return winninghits;
	}

	/**
	 * Modificateur des coups gagnants.
	 * @param winninghits Nouvelle <code>List<Node></code> représentant les coups gagnants.
	 * @since 1.0
	 * @version 1.0
	 */
	public void setWinninghits(List<Node> winninghits) {
		this.winninghits = winninghits;
	}
}
