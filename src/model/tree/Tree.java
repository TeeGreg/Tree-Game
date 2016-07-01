package model.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * Classe <code>Tree</code> permettant de générer des arbres. 
 * V1.4 :
 * <ul>
 * 	<li>Ajout de la méthode <code>clearCourse</code> permettant de redéfinir correctement les profondeurs des <code>Nodes</code> d'un arbre après suppression.</li>
 * </ul>
 * 
 * V 1.3 : 
 * <ul>
 * 	<li>Ajout des méthodes <code>searchCore()</code> et <code>searchComplementCore()</code> retournant le noyau d'un <code>Tree</code> ou son complémentaire ainsi que la méthode labelNodeConvertToInt(Node).</li>
 * 	<li>Ajout de la comparaison du <code>Barcode</code> des arbres dans la méthode compareTo(Tree).</li>
 * 	<li>Ajout de l'implémentation de l'interface <code>Cloneable</code> afin de faire des copies de nos arbres.</li>
 * 	<li>Ajout de la méthode <code>toString()</code> et de la méthode <code>displayUlehla()</code> afin d'avoir une représentation textuel d'un arbre.</li>
 * <ul> 
 * 
 * V 1.2 : 
 * <ul>
 * 	<li>Ajout des méthodes getToDelete(Node) et getDelete(Node) permettant de supprimer des <code>Node</code> de notre <code>Tree</code>.</li>
 * 	<li>Ajout des méthodes getBarcode() et getBarcode(Node) permettant d'identifier les arbres facilement.</li>
 * <ul> 
 * 	
 * V 1.1 : 
 * <ul>
 * 	<li>Ajout de la méthode LargeCourses et DepthCourses ici plutot que dans RandomTreeGenerated.</li>
 * 	<li>Ajout de la méthode suffixeDepthCourse ici plutot que dans RandomTreeGenerated.</li>
 * 	<li>Ajout de la méthode compareTo(Tree) permettant de comparer les arbres les uns avec les autress.</li>
 * <ul> 
 * 		
 * @author Florian, Grégoire, Nicolas
 * @date 15 nov. 2015
 * @version 1.3
 */
public class Tree implements TreeModel, Comparable<Tree>, Cloneable {
	
	/**
	 * Racine de notre <code>Tree</code> courant.
	 */
	private Node root;
	
	/**
	 * Constructeur de la classe <code>Tree</code> prenant en paramètre 
	 * un <code>Node</code> représentant la racine de notre <code>Tree</code> 
	 * @param root Racine de notre <code>Tree</code>
	 * @since 1.0
	 * @version 1.0
	 */
	public Tree(Node root) {
		this.root = root;
	}

	/**
	 * Retourne un successeur de <code>Node</code> parent que l'on passe en paramètre
	 * en fonction de l'<code>index<code> que l'on passe.
	 * @return Un successeur du <code>Node</code> passée en paramètre d'<code>index</code>.
	 * @see model.tree.Node#getSuccessor(int)
	 * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
	 */
	@Override
	public Object getChild(Object parent, int index) {
		if (parent != null && parent instanceof Node) {
			return ((Node)parent).getSuccessor(index);
		} else {
			return null;
		}
	}

	/**
	 * Retourne le nombre de successeur du <code>Node</code> passée en paramètre.
	 * @return Le Nombre de successeur ou 0 si il n'y a pas de successeur.
	 * @see model.tree.Node#getSuccessorCount()
	 * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
	 * @since 1.0
	 * @version 1.0
	 */
	@Override
	public int getChildCount(Object parent) {
		if (parent != null && parent instanceof Node) {
			return ((Node)parent).getSuccessorCount();
		} else {
			return 0;
		}
	}

	/**
	 * Retourne l'index du successeur passé en paramètre en fonction du <code>Node</code> voulu.
	 * @return <code>Index</code> du fils.
	 * @see model.tree.Node#getIndexOfSuccessor(Node)
	 * @see javax.swing.tree.TreeModel#getIndexOfChild(java.lang.Object, java.lang.Object)
	 * @since 1.0
	 * @version 1.0
	 */
	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if (parent != null && parent instanceof Node && child instanceof Node) {
			return ((Node)parent).getIndexOfSuccessor((Node)child);
		} else {
			return -1;
		}
	}

	/**
	 * Retourne la root de l'arbre généré.
	 * @return La racine.
	 * @see javax.swing.tree.TreeModel#getRoot()
	 * @since 1.0
	 * @version 1.0
	 */
	@Override
	public Object getRoot() {
		return this.root;
	}

	/**
	 * Retourne le fait que le <code>Node</code> courant soit une feuille ou non..
	 * @return true si le <code>Node</code> est une feuille, false sinon.
	 * @see javax.swing.tree.TreeModel#isLeaf(java.lang.Object)
	 * @since 1.0
	 * @version 1.0
	 */
	@Override
	public boolean isLeaf(Object node) {
		return this.getChildCount(node) == 0;
	}

	/**
	 * 
	 * @see javax.swing.tree.TreeModel#addTreeModelListener(javax.swing.event.TreeModelListener)
	 * @TODO 
	 * 	-> Implementer cela ultérieurement afin de pouvoir rendre notre arbre fonctionnel.
	 */
	@Override
	public void addTreeModelListener(TreeModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 * @see javax.swing.tree.TreeModel#removeTreeModelListener(javax.swing.event.TreeModelListener)
	 * @TODO 
	 * 	-> Implementer cela ultérieurement afin de pouvoir rendre notre arbre fonctionnel.
	 */
	@Override
	public void removeTreeModelListener(TreeModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 * @see javax.swing.tree.TreeModel#valueForPathChanged(javax.swing.tree.TreePath, java.lang.Object)
	 * @TODO 
	 * 	-> Implementer cela ultérieurement afin de pouvoir rendre notre arbre fonctionnel.
	 */
	@Override
	public void valueForPathChanged(TreePath arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Parcours en profondeur d'un arbre.
	 * On passe la root de notre arbre, puis l'on descend en profondeur pour trouver ces enfants.
	 * @param root Racine de l'arbre n'ayant pas d'enfant.
	 * @return un <code>Node</code> n'ayant pas d'enfant.
	 * @see model.tree.Tree#getChild(Object, int)
	 * @see model.tree.Tree#getChildCount(Object)
	 * @see model.tree.Tree#isLeaf(Object)
	 * @since 1.0
	 * @version 1.0
	 */
	public List<Node> prefixeDepthCourse(Node root) {
		List<Node> al = new ArrayList<Node>();
		al.add(root);
		if (this.isLeaf(root)) 
			return al;
		int i = 0;
		while (i < al.size()) {
			List<Node> tab = al.get(i).getSuccessors();
			if (tab == null) {
				i++;
			} else {
			al.addAll(i + 1, tab);
			i++;
			}
		}
		return al;
	}
	
	
	/**
	 * Parcours en largeur d'un arbre.
	 * On passe une liste de node ainsi que la profondeur que l'on souhaite atteindre,
	 * afin de générer le parcours en largeur de notre <code>Tree</code>.
	 * @param nodearray Liste de <code>Node</code> servant représentant le <code>Tree</code> que l'on parcours.
	 * @param depth Profondeur que l'on souhaite atteindre au maximum.
	 * @return une <code>List<Node></code> composant le <code>Tree</code> générer.
	 * @see model.tree.Node#getSuccessors()
	 * @since 1.0
	 * @version 1.0
	 */
	public List<Node> largeCourse(List<Node> nodearray, int depth) {
		if (depth == 0) {
			return nodearray;
		} 
		List<Node> al = new ArrayList<Node>();
		for(Node n : nodearray) {
			al.addAll(n.getSuccessors());
		}
		return largeCourse(al , depth - 1);
	}
	
	/**
	 * Redéfini la profondeur des Nodes après suppression.
	 * @param nodearray Liste des <code>Nodes</code> que l'on souhaite rendre "propre".
	 * @param depth Profondeur que l'on redéfini au <code>Node</code>
	 * @see model.tree.Node#setDepth(int)
	 * @see model.tree.Node#getSuccessors()
	 * @since 1.4
	 * @version 1.0
	 */
	public void clearCourse(List<Node> nodearray, int depth) {
		if (nodearray.isEmpty()) {
			return;
		} 
		for(Node n : nodearray) {
			n.setDepth(depth);
			n.setNimber(0);
			clearCourse(n.getSuccessors() , depth + 1);
		}
	}
		
	
	/**
	 * Génère le tableau S, représentant l'ordre suffixe par parcours en profondeur de notre <code>Tree</code>.
	 * @param root Racine de l'arbre.
	 * @return Une<code>List<Node></code> représentant l'ordre suffixe d'un arbre.
	 * @see model.tree.Node#getSuccessors()
	 * @see model.tree.Tree#isLeaf(Object)
	 * @since 1.0
	 * @version 1.0
	 */
	public List<Node> suffixeDepthCourse(Node root) {
		List<Node> al = new ArrayList<Node>();
		List<Boolean> visited = new ArrayList<Boolean>();
		al.add(root);
		visited.add(false);
		if (this.isLeaf(root)) 
			return al;
		int i = 0;
		while (i < al.size()) {
			List<Node> tab = al.get(i).getSuccessors();
			if (tab == null || visited.get(i)) {
				visited.set(i, true);
				i++;				
			} else {
				visited.set(i, true);
				al.addAll(i, tab);
				for (int a = 0; a < tab.size(); a++){
					visited.add(i, false);
				}
			}
		}
		return al;
	}

	/**
	 * Cette fonction permet de savoir la liste de tout les <code>Node</code> que l'on peut supprimer en tapant sur un <code>Node</code>.
	 * @param node <code>Node</code> que l'on souhaite supprimer. Il représente un peu la racine a partir duquel on remonte vers la vrai "racine"
	 * @return Une <code>List<Node></code> représentant les <code>Node</code> que l'on doit supprimer. 
	 * @see model.tree.Tree#prefixeDepthCourse(Node)
	 * @see model.tree.Tree#suffixeDepthCourse(Node)
	 * @see model.tree.Tree#getRoot()
	 * @since 1.2
	 * @version 1.0
	 */
	public List<Node> getToDelete(Node node) {
		Node tree = (Node) this.getRoot();
		List<Node> plist = this.prefixeDepthCourse(tree);
		if (!plist.contains(node)) {
			return new ArrayList<Node>();
		}
		List<Node> slist = this.suffixeDepthCourse(tree);
		List<Node> result = new ArrayList<Node>();
		result.add(node);
		int i = slist.indexOf(node);
		int j = i + 1;
		while (j < plist.size()) {
			if (plist.indexOf(slist.get(i)) > plist.indexOf(slist.get(j))) {
				result.add(slist.get(j));
				i = j;
				j++;
			} else {
				j++;
			}
		}
		return result;
	}

/**
 * Fonction permettant de supprimer les <code>Nodes</code>
 * @param node <code>Node</code> a partir duquel on part pour supprimer tout les <code>Nodes</code>
 * @return La <code>List<Node></code> de tout les Nodes que l'on supprime.
 * @see model.tree.Tree#getToDelete(Node)
 * @see model.tree.Node#getSuccessors()
 * @since 1.2
 * @version 1.0
 */
	public List<Node> delete(Node node) {
		List<Node> ln = this.getToDelete(node);
		Collections.reverse(ln);
		List<Node> result = new ArrayList<Node>();
		for (Node n : ln) {
			result.addAll(n.getSuccessors());
			result.remove(n);
		}
		return result;
	}

	/**
	 * Fonction permettant de retourner le "Code-Barre" d'un arbre.
	 * Ce "code-barre" permet de reconnaitre facilement un arbre d'un autre.
	 * @return Le <code>Barcode</code> de l'arbre courant.
	 * @see model.tree.Tree#getRoot()
	 * @see model.tree.Tree#getBarcode(Node)
	 * @since 1.2
	 * @version 1.0
	 */
	public Barcode getBarcode() {
		Node root = (Node) this.getRoot();
		return this.getBarcode(root);
	}
	
	/**
	 * Fonction peremttant de retourner le "code-barre" de l'arbre partant de la racine que l'on passe en paramètre.
	 * @param root Racine à partir de laquelle on part pour générer le "code-barre".
	 * @return La <code>Barcode</code> de l'arbre courant.
	 * @see model.tree.Tree#isLeaf(Object)
	 * @see model.tree.Tree#getBarcode()
	 * @see model.tree.Tree#getBarcode(Node)
	 * @see model.tree.Node#getSuccessors()
	 * @since 1.2
	 * @version 1.0
	 */
	public Barcode getBarcode(Node root) {	
		if (isLeaf(root)) {
			return new Barcode();
		}
		List<Barcode> al = new ArrayList<Barcode>();
		for (Node n : root.getSuccessors()) {
			al.add(getBarcode(n));
		}
		return new Barcode(al.toArray(new Barcode[0]));
	}
	
	/**
	 * Méthode permeettant de récupérer le noyau d'un <code>Tree</code>.
	 * Pour cela, on utilise l'ordre suffixe du parcours en profondeur d'un graphe.
	 * 
	 * V 1.1 : 
	 * <ul>
	 * 	<li>Modification de la première partie de l'algorithme. (cf : petit commentaire en dessous)</ul>
	 * <ul> 
	 * En effet, plutôt que d'utiliser la liste préfixe et suffixe d'un arbre, on vérifie directement que les <code>Node</code>, 
	 * soit des feuilles ou non et on les place donc respectivement dans le noyau ou dans le potentiel complémentaire.
	 * 	
	 * @return La <code>List<Node></code> représentant les éléments du noyau.
	 * @see model.tree.Tree#suffixeDepthCourse(Node)
	 * @see model.tree.Tree#getRoot()
	 * @see model.tree.Node#getSuccessors()
	 * @since 1.3
	 * @version 1.1
	 */
	public List<Node> searchCore() {
		List<Node> s = suffixeDepthCourse((Node) this.getRoot());
		
		List<Node> core = new ArrayList<Node>();
		List<Node> complementCore = new ArrayList<Node>();

		for (Node n : s) {
			if (this.isLeaf(n)) {
				core.add(n);
			} else {
				complementCore.add(n);
			}
		}
		
		int i = 0;
		boolean possibleCore;
		while (i < complementCore.size()) {
			possibleCore = true;
			
			for (Node nodeSuc : complementCore.get(i).getSuccessors()) {
				if (core.contains(nodeSuc)) {
					possibleCore = false;
				}
			}
			
			if (possibleCore == true) {
				core.add(complementCore.get(i));
				complementCore.remove(complementCore.get(i));
			} else {
				i++;
			}
		}

		return core;	
	}
	
	/**
	 * Méthode permettant de récuperer le complémentaire du noyau issues de la méthode serachCore.
	 * Pour cela, on supprime tout les <code>Nodes</code> de la liste des prefixe contenu dans la liste des <code>Node</code> du <code>core</code>.
	 * @return Le complémentaire du Noyau.
	 * @see model.tree.Tree#searchCore()
	 * @see model.tree.Tree#prefixeDepthCourse(Node)
	 * @see model.tree.Tree#getRoot()
	 * @since 1.3
	 * @version 1.0
	 */
	public List<Node> searchComplementCore() {
		List<Node> complementCore = this.suffixeDepthCourse((Node)this.getRoot());
		List<Node> core = this.searchCore();
		complementCore.removeAll(core);
		return complementCore;
	}
	
	/**
	 * Méthode clone permettant de retourner une copie "profonde" du <code>Tree</code>.
	 * @throws CloneNotSupportedException
	 * @since 1.3
	 * @version 1.0
	 */
	public Object clone() {
		if (root == null) {
			return new Tree(null);
		}
		List<Node> newtree = new ArrayList<Node>();
		List<Node> pref = prefixeDepthCourse(root);
		
		for (Node n : pref) {
			newtree.add(new Node(n.getLabel(),n.getDepth()));
		}
		
		for (Node n : newtree) {
			List<Node> ln = new ArrayList<Node>();
			for (Node n2 : pref.get(newtree.indexOf(n)).getSuccessors()) {
				 ln.add(newtree.get(pref.indexOf(n2)));
			}
			n.setSuccessors(ln);
		}
		
		
		return new Tree(newtree.get(0));
	}

	
	/**
	 * Méthode permettant de comparer entre eux deux arbres.
	 * On retourne 0 si les abres sont identiques, et -1 dans le cas contraire.
	 * @param t représente l'abre que l'on compare à notre arbre courant.
	 * @return 0 si les <code>Tree</code> sont équivalent, autre chose sinon.
	 * @see model.tree.Tree#compareNbNode(Tree)
	 * @see model.tree.Tree#compareDepth(Tree)
	 * @see model.tree.Tree#compareNodePerDepth(Tree)
	 * @see model.tree.Tree#compareChildPerDepth(Tree)
	 * @see model.tree.Tree#getBarcode()
	 * @since 1.1
	 * @version 1.1
	 */
	public int compareTo(Tree t) {
		if (this.getBarcode().equals(t.getBarcode())) {
			return 0;
		}
		return -1;
	}
	
	/**
	 * Méthode permettant de parcourir un arbre via son tableau prefixe du parcours en profondeur.
	 * Pour chaque <code>Node</code> exploré, on affiche ces enfants, ce qui permet de facilement dessiné l'arbre ou du moins se le représenter sous forme textuel.
	 * @return Une représentation textuel d'un arbre.
	 * @see model.tree.Tree#prefixeDepthCourse(Node)
	 * @see model.tree.Tree#getRoot()
	 * @see model.tree.Node#getLabel()
	 * @see model.tree.Node#getSuccessors()
	 * @see model.tree.Node#getSuccessorCount()
	 * @since 1.3
	 * @version 1.0
	 */
	public String toString() {
		List<Node> prefixe = this.prefixeDepthCourse((Node)this.getRoot());
		int index = 0;
		StringBuffer str = new StringBuffer();
		for (Node n : prefixe) {
			str.append("Node " + n.getLabel() + " ayant pour enfants : ");
			if (n.getSuccessorCount() != 0) {
				for (Node child : n.getSuccessors()) {
					str.append(child.getLabel());
					if (index < n.getSuccessorCount() - 1) {
						str.append(",");
						index++;
					}
				}
				index = 0;
			}
			str.append("\n");
		}
		return str.toString();
	}



}