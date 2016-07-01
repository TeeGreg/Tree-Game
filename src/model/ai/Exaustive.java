package model.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;



import model.tree.Barcode;
import model.tree.GhostTree;
import model.tree.Node;
import model.tree.SubTrees;
import model.tree.Tree;

/**
 * Classe représentant la méthode <code>Exaustive</code>.
 * Le principe de cette méthode est de chercher l'intégralité des coups gagnant issus d'un arbre
 * que l'on utilise pour jouer.
 * V 1.1 :
 * <ul>
 * 	<li>Ajout d'une version optimisée de <code>decoupe(GhostTree)</code> plus rapide et efficace.</li>
 * </ul>
 * 
 * @author Grégoire
 * @date 11 déc. 2015
 * @version 1.1
 */
public class Exaustive extends AI {

	/**
	 * <code>TreeMap</code> représentant les sous arbre issus de notre arbre courant
	 * représenter sous forme de <code>Barcode</code> ainsi qu'un <code>SubTree</code>
	 */
	TreeMap<Barcode, SubTrees> sousArbres;

	/**
	 * Constructeur de la classe <code>Exaustive</code> effectuant la recherche par méthode <code>Exaustive</code>/
	 * @param t <code>Tree</code> avec lequel on joue et sur lequel on effectue la méthode <code>Exaustive</code>.
	 * @see model.ai.AI#getTree()
	 * @see model.ai.Exaustive#decoupe(GhostTree)
	 * @see model.ai.Exaustive#parcourir()
	 * @see model.ai.Exaustive#generateWiningHits()
	 * @see model.tree.Tree#getRoot()
	 * @since 1.0
	 * @version 1.0
	 */
	public Exaustive(Tree t) {
		super(t);
	}
	
	/**
	 * Méthode retournant l'ensembles des sous-abres de notre arbre courant.
	 * @return L'ensemble des sous-arbre issus de notre arbre courant.
	 * @since 1.0
	 * @version 1.0
	 */
	public TreeMap<Barcode, SubTrees> getSousArbres() {
		return this.sousArbres;
	}

	/**
	 * Modificateur de l'attribut <code>sousarbre</code>.
	 * @param sousArbres Nouveaux sous-arbre que l'on passe a notre <code>Tree</code>.
	 * @since 1.0
	 * @version 1.0
	 */
	public void setSousArbres(TreeMap<Barcode, SubTrees> sousArbres) {
		this.sousArbres = sousArbres;
	}

	/**
	 * Méthode optimale permettant de découper au mieux l'arbre.
	 * Celle-ci est à préféré à <code>decoupe2(GhostTree)</code> qui est moins optimisée.
	 * @param t Arbre sur lequel on effectue la découpe.
	 * @see model.tree.Tree#getBarcode()
	 * @see model.tree.Tree#getRoot()
	 * @see model.tree.Tree#prefixeDepthCourse(Node)
	 * @see model.tree.Tree#delete(Node)
	 * @see model.tree.Barcode#generateTree()
	 * @since 1.1
	 * @version 1.0
	 */
	private void decoupe(GhostTree t) {
		Barcode currentkey = t.getBarcode();
		sousArbres.put(currentkey, new SubTrees(null, null));		
		while (currentkey !=  null) {
			List<Barcode> succ = new ArrayList<Barcode>();
			t = new GhostTree((Node) currentkey.generateTree().getRoot());
			for (Node n : t.prefixeDepthCourseGhost((Node) t.getRoot())) {					
				GhostTree gt = new GhostTree(t.delete(n));
				Barcode bc = gt.getBarcode();
				succ.add(bc);
				sousArbres.put(bc, null);
			}	
			succ.remove(0);
			sousArbres.put(currentkey, new SubTrees(succ, null));
			currentkey = sousArbres.lowerKey(currentkey);
		}
	}

	/**
	 * Méthode permettant de parcourir la liste de tout les sous-arbre afin d'en déduire
	 * ceux possédant un coup gagnant.
	 * @see model.tree.SubTrees#setHasWinningHit(Boolean)
	 * @see model.tree.SubTrees#isLosingHit()
	 * @see model.tree.SubTrees#getBc()
	 * @since 1.0
	 * @version 1.0
	 */
	private void parcourir() {
		Barcode fk = this.sousArbres.firstKey();
		SubTrees st = this.sousArbres.get(fk);
		st.setLosingHit(true);
		this.sousArbres.put(fk, st);
		for (Barcode key : this.sousArbres.keySet()) {
			SubTrees st1 = this.sousArbres.get(key);
			if (st1.isLosingHit() == null) {
				boolean b = true;
				label : for (Barcode bc : st1.getBc()) {
					if (this.sousArbres.get(bc).isLosingHit()) {
						b = false;
						break label;
					}
				}
				st1.setLosingHit(b);
				this.sousArbres.put(key, st1);	
			}
		}
	}
	
	/**
	 * Méthode permettant de générer les coups gagnants issues des sous-arbres de notre <code>Tree</code> courant.	 * 
	 * @see model.ai.AI#getTree()
	 * @see model.tree.Tree#prefixeDepthCourse(Node)
	 * @see model.tree.Tree#getRoot()
	 * @see model.tree.Tree#delete(Node)
	 * @see model.tree.Tree#getBarcode()
	 * @see model.tree.SubTrees#getBc()
	 * @see model.tree.SubTrees#isLosingHit()
	 * @since 1.0
	 * @version 1.0
	 */
	private void generateWiningHits() {
		for (Node n : this.getTree().prefixeDepthCourse((Node) this.getTree().getRoot())) {
			if (this.sousArbres.get((new GhostTree((this.getTree().delete(n)))).getBarcode()).isLosingHit()) {
				super.getWinninghits().add(n);
			}
		}
	}

	public void compute() {
		this.sousArbres = new TreeMap<Barcode, SubTrees>();
		this.decoupe(new GhostTree((Node) getTree().getRoot()));
		this.parcourir();
		this.generateWiningHits();
		
	}
}
