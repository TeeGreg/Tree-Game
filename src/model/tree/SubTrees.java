package model.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe <code>SubTrees</code> représentant la liste des sous-arbre sous forme de <code>Barcode</code>
 * ainsi qu'un<code>Boolean</code> indiquant si un coup est gagnant dans la liste de sous-arbre ou non.
 * @author Grégoire
 * @date 11 déc. 2015
 * @version 1.0
 */
public class SubTrees {
	
	/**
	 * Représente la liste de tout les sous-arbre sous forme de "code-barre".
	 */
	private List<Barcode> bc;
	
	/**
	 * Indique si on a un coup gagnant ou non.
	 */
	private Boolean losingHit;
	
	/**
	 * Constructeur de <code>SubTree</code> représentant la <code>List<Barcode></code> ainsi que la potentialité 
	 * de coup gagnant ou non. 
	 * @param b <code>List<Barcode></code> représentant les sous arbre sous forme de "code-barre". 
	 * @param h Représente le <code>Boolean</code> indiquant si le sous arbre est gagnant, perdant ou inconnu.
	 * @see model.tree.SubTrees#setBc(List)
	 * @see model.tree.SubTrees#setHasWinningHit(Boolean)
	 * @since 1.0
	 * @version 1.0
	 */
	public SubTrees(List<Barcode> b, Boolean h) {
		this.setBc(b);
		this.setLosingHit(h);
	}

	/**
	 * Retourne un <code>Boolean</code> indiquant si l'on gagne, perd ou si on ne sait pas pour le sous-arbre donnée.
	 * @return True / False / Null suivant si on a un coup gagnant, perdant ou inconnu.
	 * @since 1.0
	 * @version 1.0
	 */
	public Boolean isLosingHit() {
		return this.losingHit;
	}

	/**
	 * Modificateur de <code>hasWinningHit</code> représentant un booleen indiquant si l'on a un coup gagnant ou non.
	 * @param hasWinningHit Nouvelle valeur de notre boolean <code>hasWinningHit</code>.
	 * @since 1.0
	 * @version 1.0
	 */
	public void setLosingHit(Boolean hasWinningHit) {
		this.losingHit = hasWinningHit;
	}

	/**
	 * Retourne une copie de la Liste des potentielles coups gagnant.
	 * @return Une copie des "coups gagnant".
	 * @see model.tree.Barcode
	 * @since 1.0
	 * @version 1.0
	 */
	public List<Barcode> getBc() {
		List<Barcode> l = new ArrayList<Barcode>();
		for (Barcode b : this.bc) {
			l.add(b);
		}
		return l;
	}

	/**
	 * Modificateur de <code>bc</code> représentant la <code>List<Barcode></code> sous forme de <code>Barcode</code>.
	 * @param bc Nouvelle <code>List<Barcode></code>.
	 * @since 1.0
	 * @version 1.0
	 */
	public void setBc(List<Barcode> bc) {
		this.bc = bc;
	}
}
