<?php

namespace ContratBundle\Repository;

use ContratBundle\Entity\Contrat;
use Doctrine\DBAL\DBALException;

/**
 * ContratRepository
 *
 * This class was generated by the Doctrine ORM. Add your own custom
 * repository methods below.
 */
class ContratRepository extends \Doctrine\ORM\EntityRepository
{
    public function ajouterContrat(Contrat $cnt){
        $sql = "INSERT INTO Contrat (id_client,type,date_debut,date_echeance) VALUES (1,'Voiture',NULL,NULL)";
        try {
            $em = $this->getEntityManager()->getConnection()->exec($sql);
        } catch (DBALException $e) {
            echo "error";
        }
    }
}