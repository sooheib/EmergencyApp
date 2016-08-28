-- phpMyAdmin SQL Dump
-- version 4.2.10
-- http://www.phpmyadmin.net
--
-- Client :  localhost:8889
-- Généré le :  Dim 02 Août 2015 à 14:16
-- Version du serveur :  5.5.38
-- Version de PHP :  5.6.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Base de données :  `urgence2`
--

-- --------------------------------------------------------

--
-- Structure de la table `fiche_patient`
--

CREATE TABLE `fiche_patient` (
`id` int(11) NOT NULL,
  `fiche_json` varchar(1500) NOT NULL,
  `last_modif` varchar(256) NOT NULL,
  `id_patient` int(11) NOT NULL,
  `notice` varchar(1500) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `fiche_patient`
--

INSERT INTO `fiche_patient` (`id`, `fiche_json`, `last_modif`, `id_patient`, `notice`) VALUES
(1, '{"salle":"Salle de Chirugie(BOX3)","sp":"1","eva":"1","type":"Non Traumatique","ccmu":"Non Urgent","fc":"1","atcd":"TABAC","t":"38","scroe":"10","circ":"AVP","pa":"1","transport":"SMUR","fr":"10","origine":"FiÃ¨vre"}', '2015-05-29 13:10:13', 18, ''),
(2, '{"salle":"Salle de Traumatologie","sp":"","eva":"","type":"Traumatique","ccmu":"","fc":"","atcd":"Aucun ATCD","t":"","scroe":"","circ":"AVP","pa":"","transport":"SMUR","fr":"","origine":"FiÃ¨vre"}', '2015-05-29 14:46:14', 19, ''),
(3, '{"salle":"Salle de Chirugie(BOX3)","sp":"1","eva":"1","type":"Traumatique","ccmu":"RÃ©animation","fc":"1","atcd":"Aucun ATCD","t":"1","scroe":"12","circ":"AVP","pa":"1","transport":"SMUR","fr":"1","origine":"FiÃ¨vre"}', '2015-05-29 14:50:00', 20, ''),
(4, '{"salle":"Salle de Chirugie(BOX3)","hdm":"z&","sp":"1","delai":"0Heure","eva":"1","type":"Traumatique","ccmu":"RÃ©animation","motif":"z&","fc":"1","t":"1","scroe":"12","circ":"AVP","pa":"1","transport":"SMUR","fr":"1","origine":"FiÃ¨vre"}', '2015-05-30 00:20:53', 21, ''),
(5, '{"salle":"Salle de Chirugie(BOX3)","hdm":"sqsq","sp":"","eva":"","type":"Traumatique","ccmu":"","motif":"","fc":"","t":"","scroe":"","circ":"AVP","pa":"","transport":"SMUR","fr":"","origine":"FiÃ¨vre"}', '2015-06-01 00:38:03', 24, ''),
(6, '{"transport":"SMUR","origine":"*FiÃ¨vre","circ":"* traumatique: vital/non vital","fr":"","sp":"","fc":"","pa":"","t":"","eva":"","scroe":"","ccmu":"","salle":"Box4","hdm":"","motif":"","type":"Traumatique"}', '2015-08-02 12:27:59', 18, ''),
(7, '{"transport":"SMUR","origine":"*FiÃ¨vre","circ":"* traumatique: vital/non vital","fr":"","sp":"","fc":"","pa":"","t":"","eva":"","scroe":"","ccmu":"","salle":"Sortie","hdm":"","motif":"","type":"Traumatique"}', '2015-08-02 13:12:34', 19, '');

-- --------------------------------------------------------

--
-- Structure de la table `patient`
--

CREATE TABLE `patient` (
`id` int(11) NOT NULL,
  `nom` varchar(256) NOT NULL,
  `prenom` varchar(256) NOT NULL,
  `dns` varchar(256) NOT NULL,
  `pathimage` varchar(256) NOT NULL,
  `sexe` varchar(256) NOT NULL,
  `tel` varchar(256) NOT NULL,
  `typecarnet` varchar(256) NOT NULL,
  `dateInscirption` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `etat` int(11) NOT NULL DEFAULT '0',
  `salle` int(11) NOT NULL DEFAULT '1',
  `sortie` int(11) DEFAULT '0',
  `appel` int(11) NOT NULL DEFAULT '0',
  `salle_appel` varchar(256) NOT NULL,
  `gravity` varchar(256) NOT NULL,
  `delai` varchar(256) NOT NULL,
  `profession` varchar(256) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `patient`
--

INSERT INTO `patient` (`id`, `nom`, `prenom`, `dns`, `pathimage`, `sexe`, `tel`, `typecarnet`, `dateInscirption`, `etat`, `salle`, `sortie`, `appel`, `salle_appel`, `gravity`, `delai`, `profession`) VALUES
(13, 'Foaa', 'Naaa', '24/07/', '', 'Homme', '&', '1', '2015-05-24 22:09:33', 1, 1, 0, 0, '', '', '', ''),
(14, 'a', 'a', 'a', '', 'Homme', 'a', 'a', '2015-05-24 22:13:10', 1, 1, 0, 0, '', '', '', ''),
(15, 'a', 'a', 'a', '', 'Homme', '122', 'a', '2015-05-27 21:35:20', 1, 1, 0, 0, '', '', '', ''),
(16, 'a', 'a', '24/', '', 'Homme', 'eaea', 'eaa', '2015-05-29 10:29:16', 1, 1, 0, 1, '', '', '', ''),
(17, 'hamma', 'jahahah', 'nalkj', '', 'Homme', 'zaza', 'azza', '2015-05-29 10:29:35', 1, 1, 0, 1, '', '', '', ''),
(18, 'b', 'b', 'b', '', 'Homme', 'b', 'b', '2015-05-29 12:07:49', 1, 3, 0, 1, '', '', '', ''),
(19, 'sqsqs', 'qsqs', 'qsq', '', 'Homme', 'sqsq', 'sqsq', '2015-05-29 12:11:04', 1, 10, 0, 1, '', '', '', ''),
(20, 'hammadi', 'jbali', '2323', '', 'Homme', '12345', 'nid', '2015-05-29 13:47:52', 1, 4, 0, 1, '', '', '', ''),
(21, 'Asma', 'Jomni', '24/07/2', '', 'Homme', '12345', '1', '2015-05-29 23:20:17', 0, 4, 1, 0, '', '', '', ''),
(22, 'a', 'a', 'ssa', '', 'Homme', 'sa', 'sa', '2015-05-31 16:47:35', 0, 1, 1, 0, '', '', '', 'sa'),
(23, 'a', 'a', 'a', '', 'Homme', 'a', 'a', '2015-05-31 23:33:12', 1, 1, 0, 1, '', '', '', 'a'),
(24, 'b', 'b', 'b', '', 'Homme', 'b', 'b', '2015-05-31 23:33:24', 1, 4, 0, 1, '', '', '', 'b'),
(25, 'c', 'c', 'c', '', 'Homme', 'c', 'c', '2015-05-31 23:33:36', 1, 1, 0, 1, '', '', '', 'c'),
(26, 'd', 'd', 'd', '', 'Homme', 'd', 'd', '2015-05-31 23:33:47', 1, 1, 0, 1, '', '', '', 'd'),
(27, 'a', 'a', 'a', '', 'Homme', 'a', 'a', '2015-06-01 20:54:25', 1, 1, 0, 1, '', '', '', 'a'),
(28, 'b', 'b', 'bb', '', 'Homme', 'e', 'b', '2015-06-01 20:54:40', 1, 1, 0, 1, '', '', '', 'e'),
(29, 'z', 'e', 'e', '', 'Homme', 'e', 'e', '2015-06-01 20:54:51', 1, 1, 0, 1, '', '', '', 'e'),
(30, 'ezez', 'ezez', 'ezezez', '', 'Femme', 'eezez', 'ezez', '2015-06-01 20:55:04', 1, 1, 0, 1, '', '', '', 'ezez'),
(31, 'Nidhal', 'Saoud', 'test', '', 'Homme', '80', 'CNAM/CAVIS', '2015-08-02 10:44:31', 1, 1, 0, 1, '', '', '', 'Agriculture; plantations; autres secteurs ruraux'),
(32, 'gctv', 'yvgvvt', 'vttv', '', 'Homme', 'ygyg', 'CNAM/CAVIS', '2015-08-02 10:49:44', 0, 1, 0, 0, '', '', '', 'Foresterie; bois; pote et papier');

-- --------------------------------------------------------

--
-- Structure de la table `salle`
--

CREATE TABLE `salle` (
`id` int(11) NOT NULL,
  `nomsalle` varchar(256) NOT NULL,
  `descriptionsalle` varchar(256) NOT NULL,
  `id_responsable` int(11) NOT NULL,
  `medicale` int(11) NOT NULL DEFAULT '1',
  `code_salle` int(11) NOT NULL,
  `login` varchar(256) NOT NULL,
  `pass` varchar(256) NOT NULL,
  `salees` varchar(256) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `salle`
--

INSERT INTO `salle` (`id`, `nomsalle`, `descriptionsalle`, `id_responsable`, `medicale`, `code_salle`, `login`, `pass`, `salees`) VALUES
(5, 'Box1', 'Box1', 0, 0, 0, 'a', 'a', 'all'),
(6, 'Box2', 'Box2', 0, 0, 1, 'b', 'b', '7,8,9,10'),
(8, 'Box3', 'Box3', 0, 5, 2, 'c', 'c', '3,4,6,5,8,9,11,10'),
(20, 'Box4', 'Box4', 2, 5, 3, 'bicha', 'farhoud', '2,6,5,8,9,11,10'),
(21, 'Box5', 'Box5', 3, 5, 4, 'bicha', 'bicha', '2,6,5,8,9,11,10'),
(22, 'Reanimation', 'Reanimation', 1, 5, 5, 'k', 'k', '7,8,10'),
(23, 'SAUV', 'SAUV', 1, 5, 6, 'l', 'l', '7,5,9,8,10'),
(24, 'UHCD', 'UHCD', 1, 5, 7, 'h', 'h', '8,5,10'),
(25, 'Soins', 'Soins', 1, 5, 11, 'n', 'n', '2,3,4,10'),
(26, 'Radio Centrale', 'Radio Centrale', 1, 5, 8, 'v', 'v', '2,3,4,6,10'),
(27, 'Radio Urgence', 'Radio Urgence', 1, 5, 9, 'h', 'h', '1,2,3,4,10'),
(28, 'Sortie', 'Sortie', 1, 5, 10, 'sortie', 'sortie', 'all');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
`id` int(11) NOT NULL,
  `nom` varchar(256) NOT NULL,
  `prenom` varchar(256) NOT NULL,
  `grade` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Index pour les tables exportées
--

--
-- Index pour la table `fiche_patient`
--
ALTER TABLE `fiche_patient`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `patient`
--
ALTER TABLE `patient`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `salle`
--
ALTER TABLE `salle`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
 ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `fiche_patient`
--
ALTER TABLE `fiche_patient`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT pour la table `patient`
--
ALTER TABLE `patient`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=33;
--
-- AUTO_INCREMENT pour la table `salle`
--
ALTER TABLE `salle`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=29;
--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;