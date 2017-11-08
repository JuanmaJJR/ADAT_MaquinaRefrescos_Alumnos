
--
-- Database: `ADAT_pruebahibernate`
--

CREATE DATABASE IF NOT EXISTS ADAT_pruebahibernate;
USE ADAT_pruebahibernate;


-- --------------------------------------------------------

--
-- Table structure for table `empleados`
--

CREATE TABLE IF NOT EXISTS `empleados` (
  `id` int(11) NOT NULL,
  `nombre` varchar(30) COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Dumping data for table `empleados`
--

INSERT INTO `empleados` (`id`, `nombre`) VALUES
(1, 'Juan'),
(2, 'Paco');