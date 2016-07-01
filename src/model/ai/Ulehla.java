package model.ai;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import java.util.TreeSet;



import model.tree.Node;
import model.tree.Tree;

/**
 * Classe représentant la méthode <code>Ulehla</code>.
 * Le principe de cette méthode est de chercher directement les coups gagnant de manière rapide, puissante et efficace.
 * @author Grégoire, Nicolas
 * @date 11 déc. 2015
 * @version 1.0
 */
public class Ulehla extends AI {
	
	/**
	 * Liste de la parité de la foret d'arborescence.
	 * On ajoute un "0", si le nombre de racine Blanche est paire, "1", sinon.
	 */
	private Map<Integer, Integer> parity;
	
	/**
	 * L'<code>Integer</code> représente le tour de découpe.
	 * La <code>List<Tree></code> représente l'état de la foret au tour X.
	 */
	private Map<Integer, Tree> hitsPerTurn;
	
	/**
	 * Constructeur de la classe Ulehla  
	 * @param t <code>Tree</code> sur lequel on effectue notre algortihme.
	 * @ince 1.0
	 * @version 1.0
	 */
	public Ulehla(Tree t) {
		super(t);
		this.parity = new HashMap<Integer, Integer>();
		this.hitsPerTurn = new HashMap<Integer, Tree>();
		simpleUlehla();
	}
	

	private void simpleUlehla() {
		Tree t = (Tree) super.getTree().clone();
		int i = 0;
		do {
			t = stonning(i, t);
			i++;
		} while (t != null);
		this.setWinninghits(winningHit());
		
	}
	/**
	 * Méthode de "dénoyautage" permettant a partir d'un arbre donnée de calculer sa parité,
	 * de le colorer selon les règles établis par Ulehla, de trouver le Nimber auquel rattacher les Nodes seule
	 * découper l'arbres en supprimers les Nodes blanches et renvoyer le nouvel arbres représentant le résultat des opérations.
	 * <ul>
	 * 	<li>Nettoyer la couleur de notre arbre.</li>
	 * 	<li>Colorer notre Arbre.</li>
	 * 	<li>Calculer la parité de cette arbre est l'ajouter a notre Map.</li>
	 * 	<li>Trouver le Nimber de notre Arbre.</li>
	 * 	<li>Associer les bons Nodes aux bons parents.</li>
	 * 	<li>Selectionner la bonne racine.</li>
	 * </ul>
	 * @param tree <code>Tree</code> sur lequel on effectue notre algortihme.
	 * @return Un arbre auquel on a supprimer les <code>Nodes</code> indésirable.
	 * @see model.ai.Ulehla#cleanColor(Tree)
	 * @see model.ai.Ulehla#coloreNode(Tree)
	 * @see model.ai.Ulehla#addParity(int, Tree)
	 * @see model.ai.Ulehla#getNimber(Tree)
	 * @see model.tree.Tree#Tree(Node)
	 * @see model.tree.Tree#prefixeDepthCourse(Node)
	 * @see model.tree.Tree#getRoot()
	 * @see model.tree.Node#getColor()
	 * @see model.tree.Node#getSuccessors()
	 * @see model.tree.Node#getLabel()
	 * @see model.tree.Node#getDepth()
	 * @see model.tree.Node#getColor()
	 * @see model.tree.Node#setSuccessors(List)
	 * @see model.tree.Node#WHITE
	 * @see model.tree.Node#BLACK
	 * @since 1.0
	 * @version 1.0
	 */

	
	public Tree stonning(int turn, Tree tree) {
		
		List<Node> l = new ArrayList<Node>();
		l.add((Node) tree.getRoot());
		tree.clearCourse(l, 0);
		getNimber(tree);
		parity.put(turn,  ((Node) tree.getRoot()).getNimber() == 0 ? 1 : 0);
		hitsPerTurn.put(turn, (Tree) tree.clone());
		if (((Node) (tree.getRoot())).getSuccessorCount() == 0) return null;
		List<Node> complementCore = tree.searchComplementCore();	
		List<Node> core = tree.searchCore();
		for (Node n : complementCore) {
			for (Node n2 : n.getSuccessors()) {
				if (n2.getNimber() == 0) {
					for (Node n3 : n2.getSuccessors()) {
						if (complementCore.contains(n3)) {
							List<Node> l11 = n.getSuccessors();
							l11.add(n3);
							n.setSuccessors(l11);				
				 		}
				 	}
				}
			 } 			
		}
		for (Node n : complementCore) {
			List<Node> l11 = n.getSuccessors();
			 l11.removeAll(core);
			 n.setSuccessors(l11);
		}
		
		Node root = (Node) tree.getRoot();
		if (root.getNimber() != 0) {
			return new Tree(root);
		}
		List<Node> toadd = new ArrayList<Node>();
		int greatestnim = 0;
		for (Node n : root.getSuccessors()) {
			if (n.getNimber() != 0) {
				toadd.add(n);
			}
			if (n.getNimber() > greatestnim) {
				greatestnim = n.getNimber();
				root = n;
			}
		}
		toadd.remove(root);
		List<Node> l11 = root.getSuccessors();
		l11.addAll(toadd);
		root.setSuccessors(l11);
		return new Tree(root);
	}
	
	/**
	 * Méthode permettant de récupéré le <code>nimber</code>.
	 * Ici, le <code>nimber</code> correspond au <code>Node</code> le plus "loin" du noyau et correspond donc
	 * à la <code>Node</code> auquel on doit rattacher tout les arbres se retrouvant seul après coloriage.
	 * Cette méthode fonctionne de la manière suivante :
	 * <ul>
	 * 	<li>On récupère le noyau ainsi que l'ordre suffixe (noté S) de notre arbre.</li>
	 * 	<li>On parcours S en vérifiant que le <code>Node</code> parcouru n'appartient pas au noyau, dans ce cas, on modifie le <code>Nimber</code> à 1.</li>
	 * 	<li>On parcours ensuite à nouveau S, en regardant cette fois-ci les successeurs du alors notre <code>nimber</code> max prends la valeur du nouveau <code>Nimber</code> de l'enfant. On modifie alors la valeur du <code>Nimber</code> du <code>Node</code> examniner.</li>
	 * 	<li>Pour finir, on recupère le <code>Node</code> ayant le plus grand <code>Node</code> examiné, si un enfant possède un <code>Nimber</code> > au <code>nimber</code> max detécté,
	 * alors notre <code>nimber</code> max prends la valeur du nouveau <code>Nimber</code> de l'enfant. On modifie alors la valeur du <code>Nimber</code> du <code>Node</code> examniner.</li>
	 * 	<li>Pour finir, on recupère le <code>Node</code> ayant le plus grand <code>Nimber</code>, c'est à dire la plus grande priorité afin qu'il devienne la nouvelle racine de rattachement.</li>
	 * 	<li>Enfin, on renvoie ce <code>Node</code> candidat.</li>
	 * </ul>
	 * @return Le <code>Node</code> auquel on rattache les arbres ou noeud "seul".
	 * @see model.tree.Tree#searchCore()
	 * @see model.tree.Tree#suffixeDepthCourse(Node)
	 * @see model.tree.Tree#getRoot()
	 * @see model.tree.Node#setNimber(int)
	 * @see model.tree.Node#getSuccessors()
	 * @see model.tree.Node#getNimber()
	 * @since 1.0
	 * @version 1.0
	 */
	public void getNimber(Tree tree) { 
		List<Node> complementCore = tree.searchComplementCore();	
		for (Node n : complementCore)
			n.setNimber(1);
		
		for (Node n : complementCore) {
		TreeSet<Integer> set = new TreeSet<Integer>();
		for (int i = 1; i < tree.getBarcode().getSize(); i++) {
			set.add(i);
		}

			for (Node n2 : n.getSuccessors()) {
				set.remove(n2.getNimber());
			}
			n.setNimber(set.first());
		}

	}
	
	/**
	 * Méthode retournant une copie de la liste de parité en la calculant en fonction
	 * du nombre de racine blanche présente au total sur le jeu.
	 * @return Une copie de la <code>List</code> de parités.
	 * @since 1.0
	 * @version 1.0
	 */
	public List<Integer> getParity() {
		List<Integer> copy = new ArrayList<Integer>();
		for (Integer key : this.parity.keySet()) {
			copy.add(this.parity.get(key) % 2);
		}
		return copy;
	}
	
	/**
	 * Retourne <code>true</code>, si il y a un coup gagnant, <code>false</code> sinon.
	 * @return <code>true</code> ou <code>false</code> si il y a un coup gagnant ou non.
	 * @see model.ai.Ulehla#hasWinningHits()
	 * @since 1.0
	 * @version 1.0
	 */
	public boolean hasWinningHits() {
		if (this.getParity().contains(1)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Fonction retournant la <code>List<Tree></code> possèdant le plus grand k.
	 * (cf énoncé pour plus d'informations sur le k, voir petit 6. ).
	 * @return Une <code>List<Tree></code> dans lequel on supprimera une racine blanche.
	 * @see model.ai.Ulehla#getParity()
	 * @since 1.0
	 * @version 1.0
	 */
	public Tree treesForWin() {
		int lastIndexOfOne = this.getParity().lastIndexOf(1);
		return this.hitsPerTurn.get(lastIndexOfOne);
	}
	
	/**
	 * Retourne le <code>Node</code> marquant a coup sur un coup gagnant.
	 * @return Le coup gagnant issues d'une forêt quelconque, si il existe.
	 * @see model.ai.Ulehla#treesForWin()
	 * @see model.tree.Tree#getRoot()
	 * @see model.tree.Node#getColor()
	 * @see model.tree.Node#WHITE
	 * @since 1.0
	 * @version 1.0
	 */
	public List<Node> winningHit() {
		List<Node> l = new ArrayList<Node>();
		List<Node> result = new ArrayList<Node>();
		l.add((Node) treesForWin().getRoot());
		int num = this.getParity().lastIndexOf(1) - 1;

		while (num >= 0) {
			Tree curtree =  this.hitsPerTurn.get(num);
			this.getNimber(curtree);
			List<Node> pref = curtree.prefixeDepthCourse((Node) curtree.getRoot());
			for (Node n2 : l) {
				for (Node n3 : pref) {
					if (n2.getLabel() == n3.getLabel()) {
						result.add(n3);
						if (n3.getSuccessorCount() != 0) {
							for (Node n4 : n3.getSuccessors()) {
								if (n4.getNimber() == 0) {
								result.add(n4);
								}
							}
						}
					}
				}
				
			}
			l.clear();
			
			l.addAll(result);
			result.clear();
			
			for (Node n1 : l) {
				List<Node> ldn = curtree.delete(n1);
				boolean parities = false;
				for (Node n2 : ldn) {  //
				   Tree t = new Tree(n2);
				   this.getNimber(t);
				   if (((Node) t.getRoot()).getNimber() == 0) {
					   parities = !parities;
				   }							   
				}
				if (!parities) {
					result.add(n1);
				}
			}
			
			--num;
			l.clear();
			l.addAll(result);
			result.clear();
		}
			
		return l;
	}
	
}