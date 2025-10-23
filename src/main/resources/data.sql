-- Crear base de datos
CREATE DATABASE IF NOT EXISTS emisiones_co2_db;
USE emisiones_co2_db;

-- Tabla de sectores
CREATE TABLE sectores (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(255) NOT NULL UNIQUE,
                          descripcion TEXT,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabla de subsectores
CREATE TABLE subsectores (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             nombre VARCHAR(255) NOT NULL,
                             tipo VARCHAR(100) NOT NULL,
                             intensidad_emisiones VARCHAR(50),
                             sector_id BIGINT,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             FOREIGN KEY (sector_id) REFERENCES sectores(id) ON DELETE CASCADE
);

-- Tabla de emisiones
CREATE TABLE emisiones (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           anio INT NOT NULL,
                           mes INT NOT NULL,
                           cantidad_tco2 DECIMAL(15,2) NOT NULL,
                           sector_id BIGINT NOT NULL,
                           subsector_id BIGINT,
                           fecha_registro DATE,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           FOREIGN KEY (sector_id) REFERENCES sectores(id) ON DELETE CASCADE,
                           FOREIGN KEY (subsector_id) REFERENCES subsectores(id) ON DELETE SET NULL,
                           INDEX idx_anio (anio),
                           INDEX idx_sector (sector_id),
                           INDEX idx_anio_mes (anio, mes)
);

-- Tabla de fuentes de datos
CREATE TABLE fuentes_datos (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               nombre VARCHAR(255) NOT NULL,
                               organismo VARCHAR(255),
                               url TEXT,
                               es_confiable BOOLEAN DEFAULT TRUE,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insertar datos iniciales de sectores
INSERT INTO sectores (nombre, descripcion) VALUES
                                               ('Energía', 'Generación de energía eléctrica y calor'),
                                               ('Transporte', 'Transporte terrestre, aéreo y marítimo'),
                                               ('Industria', 'Procesos industriales y manufactura'),
                                               ('Residencial', 'Consumo energético residencial'),
                                               ('Agropecuario', 'Actividades agrícolas y pecuarias'),
                                               ('Residuos', 'Manejo y tratamiento de residuos');

-- Insertar subsectores
INSERT INTO subsectores (nombre, tipo, intensidad_emisiones, sector_id) VALUES
                                                                            ('Generación eléctrica', 'Combustibles fósiles', 'ALTA', 1),
                                                                            ('Energías renovables', 'Energía limpia', 'BAJA', 1),
                                                                            ('Transporte público', 'Transporte masivo', 'MEDIA', 2),
                                                                            ('Transporte privado', 'Vehículos particulares', 'ALTA', 2),
                                                                            ('Industria pesada', 'Manufactura', 'ALTA', 3),
                                                                            ('Industria ligera', 'Transformación', 'MEDIA', 3);