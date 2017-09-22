-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Φιλοξενητής: 127.0.0.1
-- Χρόνος δημιουργίας: 10 Ιαν 2017 στις 11:19:16
-- Έκδοση διακομιστή: 5.7.14
-- Έκδοση PHP: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Βάση δεδομένων: `universitylab`
--

DELIMITER $$
--
-- Διαδικασίες
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `countAnakoinwseis` ()  SELECT COUNT(*) FROM anakoinwseis$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `countCommonErga` ()  select distinct m.eponymo as ep1, m1.eponymo as ep2, GROUP_CONCAT(DISTINCT e.ergo_id ORDER BY e.ergo_id ASC) as ergoid, count(*) as synolo 
from meloi_ereynitika_erga me,meloi_ereynitika_erga me1, meloi m,meloi m1, ereynitika_erga e 
where me1.ereynitika_id = me.ereynitika_id 
and m.melos_id = me.meloi_id 
and m1.melos_id = me1.meloi_id 
and e.ergo_id = me.ereynitika_id 
and me.meloi_id!=me1.meloi_id 
and m1.melos_id<m.melos_id 
group by me.meloi_id , me1.meloi_id 
order by count(*) desc$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `countCοmmonDimosieuseis` ()  select distinct m.eponymo as ep1, m1.eponymo as ep2, GROUP_CONCAT(DISTINCT d.dimosieusi_id ORDER BY d.dimosieusi_id ASC) as dimosieysid, count(*) as synolo 
from meloi_dimosieuseis md,meloi_dimosieuseis md1, meloi m,meloi m1, dimosieuseis d 
where md1.dimosieusis_id = md.dimosieusis_id 
and m.melos_id = md.melos_id 
and m1.melos_id = md1.melos_id 
and d.dimosieusi_id = md.dimosieusis_id 
and md.melos_id!=md1.melos_id 
and m1.melos_id<m.melos_id 
group by md.melos_id , md1.melos_id 
order by count(*) desc$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `countDimosieuseis` ()  SELECT COUNT(*) FROM dimosieuseis$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `countDimosieuseisMelous` (IN `melosID` INT)  NO SQL
SELECT COUNT(*)AS "Αριθμός Δημοσιεύσεων" FROM dimosieuseis ,meloi ,meloi_dimosieuseis  WHERE melosId=meloi.melos_id AND meloi.melos_id=meloi_dimosieuseis.melos_id AND meloi_dimosieuseis.dimosieusis_id = dimosieuseis.dimosieusi_id$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `countEreynitikaMelous` (IN `melosId` INT)  SELECT COUNT(*)AS "Αριθμός Έργων" FROM ereynitika_erga ,meloi ,meloi_ereynitika_erga  WHERE melosId=meloi.melos_id AND meloi.melos_id=meloi_ereynitika_erga.meloi_id AND meloi_ereynitika_erga.ereynitika_id = ereynitika_erga.ergo_id$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `countErga` ()  SELECT COUNT(*) FROM ereynitika_erga$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `countMathimata` ()  NO SQL
SELECT COUNT(*) FROM mathimata$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `countMathimataMelous` (IN `melosId` INT)  SELECT COUNT(*) AS "Αριθμός Μαθημάτων" FROM mathimata ,meloi ,meloi_mathimata  WHERE melosId=meloi.melos_id AND meloi.melos_id=meloi_mathimata.melos_id AND meloi_mathimata.mathimata_id = mathimata.mathimata_id$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `countMeloi` ()  SELECT COUNT(*) FROM meloi$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `DimosieuseisNotInMelos` (IN `melosId` INT(11))  SELECT * FROM dimosieuseis WHERE dimosieuseis.dimosieusi_id NOT IN (SELECT dimosieuseis.dimosieusi_id FROM dimosieuseis,meloi,meloi_dimosieuseis WHERE dimosieuseis.dimosieusi_id=meloi_dimosieuseis.dimosieusis_id AND meloi_dimosieuseis.melos_id=meloi.melos_id AND meloi.melos_id=melosId)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ErgaNotInMelos` (IN `melosId` INT(11))  SELECT * FROM ereynitika_erga WHERE ereynitika_erga.ergo_id NOT IN (SELECT ereynitika_erga.ergo_id FROM ereynitika_erga,meloi,meloi_ereynitika_erga WHERE ereynitika_erga.ergo_id=meloi_ereynitika_erga.ereynitika_id AND meloi_ereynitika_erga.meloi_id=meloi.melos_id AND meloi.melos_id=melosId)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `MathimataNotInMelos` (IN `melosId` INT(11))  SELECT * FROM mathimata WHERE mathimata.mathimata_id NOT IN (SELECT mathimata.mathimata_id FROM mathimata,meloi,meloi_mathimata WHERE mathimata.mathimata_id=meloi_mathimata.mathimata_id AND meloi_mathimata.melos_id=meloi.melos_id AND meloi.melos_id=melosId)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Meloimedimosieuseis` (IN `melosId` INT(11))  SELECT dimosieuseis.* FROM meloi,dimosieuseis,meloi_dimosieuseis WHERE melosId=meloi.melos_id AND meloi.melos_id=meloi_dimosieuseis.melos_id AND dimosieuseis.dimosieusi_id=meloi_dimosieuseis.dimosieusis_id$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `meloimeerga` (IN `melosId` INT(11))  SELECT ereynitika_erga.* FROM meloi,ereynitika_erga,meloi_ereynitika_erga WHERE melosId=meloi.melos_id AND meloi.melos_id=meloi_ereynitika_erga.meloi_id AND ereynitika_erga.ergo_id = meloi_ereynitika_erga.ereynitika_id$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `MeloiMeMathimata` (IN `melosId` INT(11))  SELECT mathimata.* FROM meloi,mathimata,meloi_mathimata WHERE melosId = meloi.melos_id AND meloi.melos_id= meloi_mathimata.melos_id AND mathimata.mathimata_id = meloi_mathimata.mathimata_id$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `selectMeloiDimosieusis` (IN `dimosieusiId` INT)  select meloi.* from dimosieuseis,meloi,meloi_dimosieuseis where dimosieuseis.dimosieusi_id=dimosieusiId AND meloi.melos_id = meloi_dimosieuseis.melos_id AND meloi_dimosieuseis.dimosieusis_id=dimosieusiId$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `selectMeloiErgou` (IN `ergoId` INT(15))  select meloi.* from ereynitika_erga,meloi,meloi_ereynitika_erga where ereynitika_erga.ergo_id=ergoId AND meloi.melos_id = meloi_ereynitika_erga.meloi_id AND meloi_ereynitika_erga.ereynitika_id=ergoId$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `selectMeloiMathimatos` (IN `mathimataId` INT(15))  select meloi.* from mathimata,meloi,meloi_mathimata where mathimata.mathimata_id=mathimataId AND meloi.melos_id = meloi_mathimata.melos_id AND meloi_mathimata.mathimata_id=mathimataId$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `anakoinwseis`
--

CREATE TABLE `anakoinwseis` (
  `anakoinwsi_id` int(11) NOT NULL,
  `titlos` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  `perigrafi` varchar(450) COLLATE utf8_unicode_ci DEFAULT NULL,
  `date` date NOT NULL,
  `thematiki` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Άδειασμα δεδομένων του πίνακα `anakoinwseis`
--

INSERT INTO `anakoinwseis` (`anakoinwsi_id`, `titlos`, `perigrafi`, `date`, `thematiki`) VALUES
(12, 'Προκήρυξη επιλογής υποτρόφων ', 'Προκήρυξη επιλογής υποτρόφων χωρίς διαγωνισμό, για σπουδές Δεύτερου Κύκλου (μεταπτυχιακές) και Τρίτου Κύκλου (διδακτορικές) στο εσωτερικό, ακαδημαϊκού έτους 2015-2016', '2016-12-24', 'Υποτροφίες'),
(13, 'Πρόγραμμα εξεταστικής', 'Πρόγραμμα εξεταστικής περιόδου Ιανουαρίου-Φεβρουαρίου 2017 ', '2016-12-24', 'Εξεταστική');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `dimosieuseis`
--

CREATE TABLE `dimosieuseis` (
  `dimosieusi_id` int(11) NOT NULL,
  `titlos` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `eidos_id` int(11) NOT NULL,
  `thematiki_id` int(50) NOT NULL,
  `etos` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Άδειασμα δεδομένων του πίνακα `dimosieuseis`
--

INSERT INTO `dimosieuseis` (`dimosieusi_id`, `titlos`, `eidos_id`, `thematiki_id`, `etos`) VALUES
(12, '“A Survey of Large-Scale Analytical Query Processing in MapReduce”', 1, 6, '2013-12-01'),
(13, '"Dynamic, Behavioural-based Estimation of Resource Provisioning based on High-level Application Terms in Cloud Platforms"', 1, 5, '2016-12-01'),
(14, 'SOA Governance from a Healthcare Perspective', 2, 5, '2013-12-01'),
(15, 'New Trends in Healthcare Information Systems (HIS) Integration', 5, 6, '2013-01-01'),
(16, 'Peer-to-Peer Query Processing over Multidimensional Data', 4, 5, '2012-01-01');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `eidos_dimosieusis`
--

CREATE TABLE `eidos_dimosieusis` (
  `eidos_id` int(11) NOT NULL,
  `eidos` varchar(200) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Άδειασμα δεδομένων του πίνακα `eidos_dimosieusis`
--

INSERT INTO `eidos_dimosieusis` (`eidos_id`, `eidos`) VALUES
(1, 'Περιοδικό'),
(2, 'Συνέδριο'),
(3, 'Κεφάλαιο σε Βιβλίο'),
(4, 'Βιβλίο'),
(5, 'book: Handbook of Research on ICTs for Healthcare and Social Services');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `epipedo`
--

CREATE TABLE `epipedo` (
  `epipedo_id` int(11) NOT NULL,
  `epipedo` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Άδειασμα δεδομένων του πίνακα `epipedo`
--

INSERT INTO `epipedo` (`epipedo_id`, `epipedo`) VALUES
(1, 'Μεταπτυχιακό'),
(2, 'Προπτυχιακό'),
(3, 'Διδακτορικό'),
(4, 'Ενισχυτικό');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `ereynitika_erga`
--

CREATE TABLE `ereynitika_erga` (
  `ergo_id` int(11) NOT NULL,
  `titlos` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `etos` date NOT NULL,
  `etos_ews` date DEFAULT NULL,
  `perigrafi` varchar(500) COLLATE utf8_unicode_ci DEFAULT 'Δεν διατίθεται περιγραφή',
  `xrim_orgmanismos` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `katastasi_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Άδειασμα δεδομένων του πίνακα `ereynitika_erga`
--

INSERT INTO `ereynitika_erga` (`ergo_id`, `titlos`, `etos`, `etos_ews`, `perigrafi`, `xrim_orgmanismos`, `katastasi_id`) VALUES
(1, 'e-SENS: Electronic Simple European Networked Services', '2016-01-01', '2017-01-01', 'Electronic Simple European Networked Services', 'EU', 1),
(6, 'SEMAGROW', '2015-01-01', '2016-01-01', 'Data intensive techniques to boost the real-time performance of global agricultural data infrastructures', 'ECICT', 1),
(7, 'PINCLOUD', '2014-01-01', '2015-01-01', 'Providing Integrated eHealth Services for Personalized Medicine utilizing Cloud Infrastructure', 'GSRT', 1),
(8, 'Vision Cloud', '2010-01-01', NULL, 'Virtualised Storage Services Foundation for the Future Internet', ' EU', 2);

--
-- Δείκτες `ereynitika_erga`
--
DELIMITER $$
CREATE TRIGGER `checkinsertdater` BEFORE INSERT ON `ereynitika_erga` FOR EACH ROW begin
if new.etos_ews < new.etos then
DELETE FROM ereynitika_erga WHERE ereynitika_erga.etos = NEW.etos;
end if;
end
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `katastasi_ergou`
--

CREATE TABLE `katastasi_ergou` (
  `katastasi_id` int(11) NOT NULL,
  `katastasi` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Ολοκληρωμένο'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Άδειασμα δεδομένων του πίνακα `katastasi_ergou`
--

INSERT INTO `katastasi_ergou` (`katastasi_id`, `katastasi`) VALUES
(1, 'Ολοκληρωμένο'),
(2, 'Τρέχον'),
(3, 'Άγνωστο');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `katigoria`
--

CREATE TABLE `katigoria` (
  `katigoria_id` int(11) NOT NULL,
  `katigoria` varchar(200) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Άδειασμα δεδομένων του πίνακα `katigoria`
--

INSERT INTO `katigoria` (`katigoria_id`, `katigoria`) VALUES
(1, 'ΔΕΠ'),
(2, 'Ερευνητής'),
(3, 'Υποψήφιος Διδάκτωρ'),
(4, 'Μεταπτυχιακός'),
(5, 'Προπτυχιακός'),
(6, 'Άγνωστο'),
(7, 'Καθηγητής'),
(8, 'Λέκτορας'),
(9, 'Αναπληρωτής Καθηγητής'),
(10, 'Επίκουρος Καθηγητής');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `mathimata`
--

CREATE TABLE `mathimata` (
  `mathimata_id` int(50) NOT NULL,
  `titlos` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  `epipedo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Άδειασμα δεδομένων του πίνακα `mathimata`
--

INSERT INTO `mathimata` (`mathimata_id`, `titlos`, `epipedo`) VALUES
(1, 'Διαχείριση Επιχειρησιακών Διεργασιών', 1),
(2, ' Πληροφορικά Συστήματα', 1),
(4, 'Εισαγωγή στις Βάσεις Δεδομένων', 2),
(6, ' UML', 4),
(8, 'Πληροφοριακά Συστήματα και Υπηρεσίες', 1),
(9, 'Υπηρεσιοστρεφείς Αρχιτεκτονικές', 1),
(10, 'Διαχείριση Δεδομένων', 1),
(11, 'Νεφοϋπολογιστική', 1),
(12, 'Κινητή Υπολογιστική και Εφαρμογές', 1);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `meloi`
--

CREATE TABLE `meloi` (
  `melos_id` int(11) NOT NULL,
  `onoma` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `eponymo` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `webmail` varchar(260) COLLATE utf8_unicode_ci DEFAULT NULL,
  `short_cv` varchar(5000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tilefono` varchar(20) COLLATE utf8_unicode_ci DEFAULT 'Μη διαθέσιμο',
  `katigoria_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Άδειασμα δεδομένων του πίνακα `meloi`
--

INSERT INTO `meloi` (`melos_id`, `onoma`, `eponymo`, `webmail`, `short_cv`, `tilefono`, `katigoria_id`) VALUES
(39, 'Chara', 'Papageorge', 'Chara-pap@ole.gr', ' Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό Βιογραφικό', '639556549', 6),
(50, 'Σπυρίδων', 'Κλεφτάκης', 'spiroskleft@gmail.com', 'Διάφορα και πολλά', '6936024049', 4),
(52, 'Γεώργιος', 'Βασιλακόπουλος', 'gvass@unipi.gr', 'Ο Γεώργιος Βασιλακόπουλος είναι διδάκτορας του Πανεπιστημίου του Λονδίνου, Καθηγητής του Τμήματος Ψηφιακών Συστημάτων του Πανεπιστημίου Πειραιώς και Αντιπρύτανης Ακαδημαϊκών Υποθέσεων και Προσωπικού του Πανεπιστημίου αυτού. Ως Αντιπρύτανης Ακαδημαϊκών Υποθέσεων, είναι, εκ του νόμου, Πρόεδρος της Μονάδας Διασφάλισης Ποιότητας (ΜΟΔΙΠ) και της Δομής Απασχόλησης και Σταδιοδρομίας (ΔΑΣΤΑ) του Πανεπιστημίου Πειραιώς, και έχει την επιστημονική/τεχνική ευθύνη των διαδικασιών ανάπτυξης αντίστοιχων σύγχρονων πληροφοριακών συστημάτων. Επί σειρά ετών, έχει διατελέσει Πρόεδρος του Τμήματος Ψηφιακών Συστημάτων, Διευθυντής Προγράμματος Μεταπτυχιακών Σπουδών και Επιστημονικός Υπεύθυνος του Εργαστηρίου Ψηφιακών Υπηρεσιών Υγείας του Τμήματος αυτού. Από το έτος 1998 ως Επιστημονικός Υπεύθυνος του Κέντρου Διαχείρισης Δικτύων του Πανεπιστημίου Πειραιώς είχε την επιστημονική/τεχνική ευθύνη της αρχικής ανάπτυξης σύγχρονης δικτυακής υποδομής ενοποιημένου δικτύου φωνής και δεδομένων, καλύπτοντας όλα τα κτίρια του Πανεπιστημίου, και ως Επιστημονικός Υπεύθυνος του προγράμματος Εκπαίδευσης από Απόσταση “Εύδοξος” είχε την επιστημονική/τεχνική ευθύνη της ανάπτυξης σύγχρονης αίθουσας τηλεκπαίδευσης στο Πανεπιστήμιο Πειραιώς και της πρώτης διενέργειας μαθημάτων από απόσταση με ηλεκτρονικά μέσα (σε πραγματικό ή όχι χρόνο). Έχει εργασθεί ως σύμβουλος ή ως μέλος της διοίκησης σε επιχειρήσεις του ιδιωτικού τομέα και σε οργανισμούς του δημόσιου τομέα. Έχει διατελέσει σύμβουλος του Υπουργού Υγείας σε θέματα πληροφορικής, μέλος ΔΣ στα Νοσοκομεία “Γ. Γεννηματάς” και “Αττικόν”, σύμβουλος πληροφορικής του Εθνικού Κέντρου Άμεσης Βοήθειας και έχει συμμετάσχει σε πολλές επιτροπές Υπουργείων και Οργανισμών του ευρύτερου Δημόσιου Τομέα σε θέματα πληροφορικής και επικοινωνιών. Έχει συμμετάσχει σε εθνικά και κοινοτικά έργα έρευνας και τεχνολογικής ανάπτυξης σε θέματα πληροφορικής και επικοινωνιών, έχει διατελέσει αξιολογητής έργων και ερευνητικών προγραμμάτων (ελληνικών και ευρωπαϊκών), έχει δημοσιεύσει πολλές εργασίες σε διεθνώς αναγνωρισμένα επιστημονικά περιοδικά και πρακτικά συνεδρίων στο χώρο της Πληροφορικής, ιδιαίτερα της Πληροφορικής Υγείας, έχει συγγράψει τρία βιβλία και κεφάλαια ξενόγλωσσων βιβλίων, έχει συμμετάσχει σε πολλά διεθνή συνέδρια ως μέλος επιτροπών τους ή ως απλός συμμετέχων, και έχει υπάρξει σύμβουλος έκδοσης ή κριτής περιοδικών, πρακτικών συνεδρίων και βιβλίων. Είναι μέλος διεθνών και ελληνικών επιστημονικών ενώσεων.', '6936000222', 7),
(53, 'Μαρίνος', 'Θεμιστοκλέους', 'mthemist@unipi.gr', 'Ο Μαρίνος Θεμιστοκλέους είναι Αναπληρωτής Kαθηγητής στο Τμήμα Ψηφιακών Συστημάτων του Πανεπιστημίου Πειραιώς ενώ νωρίτερα είχε εργαστεί στο Brunel University του Λονδίνου. Οι βασικοί τομείς της επιστημονικής του δραστηριότητας αφορούν την ολοκλήρωση εφαρμογών, τα Πληροφοριακά Συστήματα (ΠΣ), την ψηφιακή οικονομία και την πληροφορική υγείας. Έχει δημοσιεύσει περισσότερα από 100 άρθρα σε επιστημονικά περιοδικά, συνέδρια και βιβλία ενώ έχει εκδώσει 4 ακαδημαϊκά συγγράμματα. Η επιστημονική του συνεισφορά έχει λάβει μεγάλο αριθμό ετεροαναφορών, έχει τιμηθεί με βραβεία αριστείας ενώ ο ίδιος έχει καταταχθεί στην 5η θέση στην Ευρώπη στη σχετική λίστα με τους πιο παραγωγικούς συγγραφείς με δημοσιεύσεις σε κορυφαία περιοδικά στο χώρο των Πληροφοριακών Συστημάτων. Κατά την περίοδο 2005-2008 διετέλεσε υπεύθυνος σύνταξης στο περιοδικό European Journal of Information Systems. Έχει συμμετάσχει σε περισσότερα από 30 ερευνητικά προγράμματα και η έρευνα του έχει χρηματοδοτηθεί από την Ευρωπαϊκή Ένωση, το Ηνωμένο Βασίλειο, δημόσιους οργανισμούς και επιχειρήσεις. Έχει διατελέσει σύμβουλος σε θέματα ολοκλήρωσης, ψηφιακής οικονομίας και ΠΣ σε Υπουργεία, δημόσιους οργανισμούς, εταιρίες πληροφορικής, οργανισμούς υγείας, τράπεζες και πολυεθνικές εταιρίες σε 6 χώρες της ΕΕ. Επέβλεψε με επιτυχία πέντε διδακτορικές διατριβές ενώ στην παρούσα φάση επιβλέπει άλλες τρεις.', '69242424', 9),
(54, 'Χρήστος ', ' Δουλκερίδης', 'cdoulk@unipi.gr', 'Ο Χρήστος Δουλκερίδης είναι Λέκτορας στο Τμήμα Ψηφιακών Συστημάτων του Πανεπιστημίου Πειραιώς. Έχει λάβει υποτροφία ERCIM “Allain Bensoussan” για μεταδιδακτορικές σπουδές στο Νορβηγικό Πολυτεχνείο Επιστημών και Τεχνολογίας το 2009. Πριν από αυτό, έλαβε διδακτορικό τίτλο σπουδών καθώς και μεταπτυχιακό τίτλο σπουδών από το Οικονομικό Πανεπιστήμιο Αθηνών το 2007 και το 2003 αντίστοιχα, και δίπλωμα ηλεκτρολόγου μηχανικού και μηχανικού ηλεκτρονικών υπολογιστών από το Εθνικό Μετσόβιο Πολυτεχνείο το 2001. Βραβεύθηκε ως δεύτερος νικητής του διαγωνισμού “Μιχάλης Δερτούζος” σχετικά με το “Ανθρώπινο Πρόσωπο της Πληροφορικής” στο 14ο παγκόσμιο συνέδριο World Congress on Information Technology (WCIT’04) το 2004. Τα ερευνητικά του ενδιαφέροντα περιλαμβάνουν επεξεργασία επερωτήσεων, διαχείριση δεδομένων σε δίκτυα ομότιμων κόμβων, κατανεμημένη εξόρυξη γνώσης, διαχείριση δεδομένων στον Παγκόσμιο Ιστό, κινητές και χωρικές βάσεις δεδομένων. Έχει δημοσιεύσει το ερευνητικό του έργο σε κορυφαία διεθνή περιοδικά (μεταξύ άλλων IEEE TKDE, IEEE J-SAC, ACM TKDD, Springer DPD) και συνέδρια (μεταξύ άλλων ACM SIGMOD, VLDB, ICDE, PKDD, SIAM SDM) στις περιοχές διαχείρισης δεδομένων, εξόρυξη γνώσης και κατανεμημένων συστημάτων.', '6969696969', 8),
(55, 'Ανδριάνα ', 'Πρέντζα', 'aprentza@unipi.gr', 'Η Ανδριάνα A. Πρέντζα σπούδασε Μηχανικός Ηλεκτρονικών Υπολογιστών & Πληροφορικής στο Πανεπιστήμιο Πατρών και στη συνέχεια απέκτησε Μεταπτυχιακό Δίπλωμα Ειδίκευσης στη Βιοϊατρική Τεχνολογία από το Διατμηματικό Πρόγραμμα Μεταπτυχιακών Σπουδών στη Βιοϊατρική Τεχνολογία του ΕΜΠ και του Πανεπιστημίου Πατρών και Διδακτορικό Δίπλωμα στη Βιοϊατρική Τεχνολογία από το Πολυτεχνείο Eindhoven, στην Ολλανδία. Κατά την περίοδο 1992-1997, εργάστηκε ως επιστημονικός συνεργάτης στο Πολυτεχνείο του Eindhoven (Τμήμα Ηλεκτρολόγων Μηχανικών). Το 1997 ξεκίνησε την επαγγελματική της σταδιοδρομία στην Ελλάδα ως Επιστημονικός Συνεργάτης του Εργαστηρίου Βιοϊατρικής Τεχνολογίας του ΕΜΠ σε θέματα Τηλεματικής Υγείας και Ιατρικής Πληροφορικής. Το 2001 κατέλαβε τη θέση της Ερευνήτριας στο Επιστημονικό Πανεπιστημιακό Ινστιτούτο Συστημάτων Επικοινωνιών και Υπολογιστών (ΕΠΙΣΕΥ) του ΕΜΠ μέχρι και το 2008 (Ερευνήτρια Β’). Σήμερα (από τον Ιανουάριο του 2009) είναι επίκουρη καθηγήτρια στο Τμήμα Ψηφιακών Συστημάτων του Πανεπιστημιού Πειραιώς. Έχει δημοσιεύσει περισσότερα από 70 επιστημονικά άρθρα σε διεθνή επιστημονικά περιοδικά και πρακτικά συνεδρίων με διαδικασία κρίσης και έχει συμμετάσχει στη συγγραφή 7 επιστημονικών βιβλίων. Τα τρέχοντα επιστημονικά της ενδιαφέροντα περιλαμβάνουν Μεθοδολογίες και τεχνικές Τεχνολογίας Λογισμικού για την ανάπτυξη και αξιολόγηση συστημάτων και υπηρεσιών λογισμικού, Ανάπτυξη Διαλειτουργικών Ψηφιακών Συστημάτων και Υπηρεσιών, Εφαρμογή μεθοδολογιών και τεχνικών Τεχνολογίας Λογισμικού για την ανάπτυξη και αξιολόγηση συστημάτων και υπηρεσιών λογισμικού στην ηλεκτρονική διακυβέρνηση (e-government), στη Βιοϊατρική και στις Υπηρεσίες Ηλεκτρονικής Υγείας (e-health). Διαθέτει πλούσια και πολύχρονη εμπειρία στο συντονισμό και σχεδιασμό προγραμμάτων και εκπόνηση επιχειρηματικών σχεδίων, και στη διαχείριση προγραμμάτων συμμετέχοντας σαν υπεύθυνη σε σημαντικά ευρωπαϊκά (FP4, FP5, FP6, FFP7) και εθνικά έργα Έρευνας και Ανάπτυξης στον τομέα της εφαρμογής Τεχνολογιών Πληροφορικής και Επικοινωνιών. Είναι αξιολογήτρια/εμπειρογνώμων για την Ευρωπαϊκή Επιτροπή και εθνικά προγράμματα. Είναι Associate Editor στο περιοδικό IEEE Transactions on the Information Technology in Biomedicine και IEEE Journal of Biomedical and Health Informatics και διατελεί κριτής σε διάφορα άλλα διεθνή επιστημονικά περιοδικά. Είναι μέλος του Τεχνικού Επιμελητηρίου Ελλάδας (ΤΕΕ) και το 2006 έγινε Senior Member του IEEE. Επίσης από το 2013 ως μέλος του Ευρωπαϊκού Γνωμοδοτικού Συμβουλίου για το πρόγραμμα «Ορίζοντας 2020», Κοινωνική Πρόκληση «Υγεία, Δημογραφική Μεταβολή και Ευεξία (Horizon 2020, Advisory Group for Health, demographic change and wellbeing).', 'Μη διαθέσιμο', 10);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `meloi_dimosieuseis`
--

CREATE TABLE `meloi_dimosieuseis` (
  `dimosieusis_id` int(11) NOT NULL,
  `melos_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Άδειασμα δεδομένων του πίνακα `meloi_dimosieuseis`
--

INSERT INTO `meloi_dimosieuseis` (`dimosieusis_id`, `melos_id`) VALUES
(12, 53),
(12, 54),
(12, 55),
(13, 39),
(13, 54),
(14, 50),
(14, 52),
(15, 50),
(15, 53),
(15, 55),
(16, 39),
(16, 50),
(16, 52),
(16, 54);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `meloi_ereynitika_erga`
--

CREATE TABLE `meloi_ereynitika_erga` (
  `ereynitika_id` int(11) NOT NULL,
  `meloi_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Άδειασμα δεδομένων του πίνακα `meloi_ereynitika_erga`
--

INSERT INTO `meloi_ereynitika_erga` (`ereynitika_id`, `meloi_id`) VALUES
(1, 39),
(6, 39),
(7, 39),
(6, 50),
(7, 50),
(8, 50),
(6, 53),
(7, 53),
(1, 54),
(6, 54),
(1, 55),
(7, 55);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `meloi_mathimata`
--

CREATE TABLE `meloi_mathimata` (
  `mathimata_id` int(11) NOT NULL,
  `melos_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Άδειασμα δεδομένων του πίνακα `meloi_mathimata`
--

INSERT INTO `meloi_mathimata` (`mathimata_id`, `melos_id`) VALUES
(1, 39),
(6, 39),
(1, 50),
(2, 50),
(2, 52),
(8, 52),
(1, 53),
(9, 53),
(8, 54),
(10, 54),
(11, 54),
(2, 55),
(8, 55),
(9, 55),
(10, 55),
(11, 55);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `thematiki_enotita`
--

CREATE TABLE `thematiki_enotita` (
  `thematiki_id` int(50) NOT NULL,
  `titlos` varchar(150) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Άδειασμα δεδομένων του πίνακα `thematiki_enotita`
--

INSERT INTO `thematiki_enotita` (`thematiki_id`, `titlos`) VALUES
(1, 'Μεταπτυχιακό'),
(2, 'Προπτυχιακό'),
(3, 'Διδακτορικό'),
(5, 'Διάφορα'),
(6, 'VLDB Journal');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `users`
--

CREATE TABLE `users` (
  `userid` int(11) NOT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `isAdmin` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Άδειασμα δεδομένων του πίνακα `users`
--

INSERT INTO `users` (`userid`, `username`, `password`, `isAdmin`) VALUES
(1, 'admin', 'admin', 1),
(2, 'user', 'user', 0);

--
-- Ευρετήρια για άχρηστους πίνακες
--

--
-- Ευρετήρια για πίνακα `anakoinwseis`
--
ALTER TABLE `anakoinwseis`
  ADD PRIMARY KEY (`anakoinwsi_id`);

--
-- Ευρετήρια για πίνακα `dimosieuseis`
--
ALTER TABLE `dimosieuseis`
  ADD PRIMARY KEY (`dimosieusi_id`),
  ADD KEY `eidos_id` (`eidos_id`),
  ADD KEY `thematiki_id_dim` (`thematiki_id`);

--
-- Ευρετήρια για πίνακα `eidos_dimosieusis`
--
ALTER TABLE `eidos_dimosieusis`
  ADD PRIMARY KEY (`eidos_id`);

--
-- Ευρετήρια για πίνακα `epipedo`
--
ALTER TABLE `epipedo`
  ADD PRIMARY KEY (`epipedo_id`);

--
-- Ευρετήρια για πίνακα `ereynitika_erga`
--
ALTER TABLE `ereynitika_erga`
  ADD PRIMARY KEY (`ergo_id`),
  ADD KEY `katastasi_id` (`katastasi_id`);

--
-- Ευρετήρια για πίνακα `katastasi_ergou`
--
ALTER TABLE `katastasi_ergou`
  ADD PRIMARY KEY (`katastasi_id`);

--
-- Ευρετήρια για πίνακα `katigoria`
--
ALTER TABLE `katigoria`
  ADD PRIMARY KEY (`katigoria_id`);

--
-- Ευρετήρια για πίνακα `mathimata`
--
ALTER TABLE `mathimata`
  ADD PRIMARY KEY (`mathimata_id`),
  ADD KEY `epipedo_id` (`epipedo`);

--
-- Ευρετήρια για πίνακα `meloi`
--
ALTER TABLE `meloi`
  ADD PRIMARY KEY (`melos_id`),
  ADD KEY `katigoria_id` (`katigoria_id`);

--
-- Ευρετήρια για πίνακα `meloi_dimosieuseis`
--
ALTER TABLE `meloi_dimosieuseis`
  ADD PRIMARY KEY (`dimosieusis_id`,`melos_id`),
  ADD KEY `dimosieusis_id` (`dimosieusis_id`),
  ADD KEY `melos_dimosieusis_id` (`melos_id`);

--
-- Ευρετήρια για πίνακα `meloi_ereynitika_erga`
--
ALTER TABLE `meloi_ereynitika_erga`
  ADD PRIMARY KEY (`ereynitika_id`,`meloi_id`),
  ADD KEY `melos_ereynitika_id` (`meloi_id`),
  ADD KEY `ereynitika_id` (`ereynitika_id`);

--
-- Ευρετήρια για πίνακα `meloi_mathimata`
--
ALTER TABLE `meloi_mathimata`
  ADD PRIMARY KEY (`mathimata_id`,`melos_id`),
  ADD KEY `melos_mathimata_id` (`melos_id`),
  ADD KEY `mathimata_id` (`mathimata_id`);

--
-- Ευρετήρια για πίνακα `thematiki_enotita`
--
ALTER TABLE `thematiki_enotita`
  ADD PRIMARY KEY (`thematiki_id`);

--
-- Ευρετήρια για πίνακα `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userid`);

--
-- AUTO_INCREMENT για άχρηστους πίνακες
--

--
-- AUTO_INCREMENT για πίνακα `anakoinwseis`
--
ALTER TABLE `anakoinwseis`
  MODIFY `anakoinwsi_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT για πίνακα `dimosieuseis`
--
ALTER TABLE `dimosieuseis`
  MODIFY `dimosieusi_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT για πίνακα `eidos_dimosieusis`
--
ALTER TABLE `eidos_dimosieusis`
  MODIFY `eidos_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT για πίνακα `epipedo`
--
ALTER TABLE `epipedo`
  MODIFY `epipedo_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT για πίνακα `ereynitika_erga`
--
ALTER TABLE `ereynitika_erga`
  MODIFY `ergo_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
--
-- AUTO_INCREMENT για πίνακα `katastasi_ergou`
--
ALTER TABLE `katastasi_ergou`
  MODIFY `katastasi_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT για πίνακα `katigoria`
--
ALTER TABLE `katigoria`
  MODIFY `katigoria_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT για πίνακα `mathimata`
--
ALTER TABLE `mathimata`
  MODIFY `mathimata_id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT για πίνακα `meloi`
--
ALTER TABLE `meloi`
  MODIFY `melos_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;
--
-- AUTO_INCREMENT για πίνακα `thematiki_enotita`
--
ALTER TABLE `thematiki_enotita`
  MODIFY `thematiki_id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT για πίνακα `users`
--
ALTER TABLE `users`
  MODIFY `userid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- Περιορισμοί για άχρηστους πίνακες
--

--
-- Περιορισμοί για πίνακα `dimosieuseis`
--
ALTER TABLE `dimosieuseis`
  ADD CONSTRAINT `eidos_id` FOREIGN KEY (`eidos_id`) REFERENCES `eidos_dimosieusis` (`eidos_id`),
  ADD CONSTRAINT `thematiki_id_dim` FOREIGN KEY (`thematiki_id`) REFERENCES `thematiki_enotita` (`thematiki_id`);

--
-- Περιορισμοί για πίνακα `ereynitika_erga`
--
ALTER TABLE `ereynitika_erga`
  ADD CONSTRAINT `katastasi_id` FOREIGN KEY (`katastasi_id`) REFERENCES `katastasi_ergou` (`katastasi_id`);

--
-- Περιορισμοί για πίνακα `mathimata`
--
ALTER TABLE `mathimata`
  ADD CONSTRAINT `epipedo_id` FOREIGN KEY (`epipedo`) REFERENCES `epipedo` (`epipedo_id`);

--
-- Περιορισμοί για πίνακα `meloi`
--
ALTER TABLE `meloi`
  ADD CONSTRAINT `katigoria_id` FOREIGN KEY (`katigoria_id`) REFERENCES `katigoria` (`katigoria_id`);

--
-- Περιορισμοί για πίνακα `meloi_dimosieuseis`
--
ALTER TABLE `meloi_dimosieuseis`
  ADD CONSTRAINT `dimosieusis_id` FOREIGN KEY (`dimosieusis_id`) REFERENCES `dimosieuseis` (`dimosieusi_id`),
  ADD CONSTRAINT `melos_dimosieusis_id` FOREIGN KEY (`melos_id`) REFERENCES `meloi` (`melos_id`);

--
-- Περιορισμοί για πίνακα `meloi_ereynitika_erga`
--
ALTER TABLE `meloi_ereynitika_erga`
  ADD CONSTRAINT `ereynitika_id` FOREIGN KEY (`ereynitika_id`) REFERENCES `ereynitika_erga` (`ergo_id`),
  ADD CONSTRAINT `melos_ereynitika_id` FOREIGN KEY (`meloi_id`) REFERENCES `meloi` (`melos_id`);

--
-- Περιορισμοί για πίνακα `meloi_mathimata`
--
ALTER TABLE `meloi_mathimata`
  ADD CONSTRAINT `mathimata_id` FOREIGN KEY (`mathimata_id`) REFERENCES `mathimata` (`mathimata_id`),
  ADD CONSTRAINT `melos_mathimata_id` FOREIGN KEY (`melos_id`) REFERENCES `meloi` (`melos_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
