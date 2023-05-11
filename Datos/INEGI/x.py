import csv
import random
import os

empresas =  [ 'Produmax',
    'MateriCorp',
    'PrimaTech Industries',
    'Resourcex',
    'PrimeMateria',
    'RawMate Solutions',
    'InnovaPrima',
    'MateriaPro',
    'PrimeIndustri',
    'EcoPrime Resources',

    'PrimaGlobal',
    'GreenMateria',
    'PrimaSphere',
    'MateriaMax',
    'FirstPrime Co.',
    'NaturePrima',
    'PrimeWay',
    'SuperiorMateria',
    'PrimeRise',
    'MateriaLogic',

    'PrimeForce',
    'MateriaZen',
    'PrimaWorld',
    'MateriaTrust',
    'PrimePulse',
    'MateriaCore',
    'PrimeNation',
    'MateriaKing',
    'PrimaResource',
    'MateriaFirst',

    'PrimeFusion',
    'MateriaVision',
    'PrimaEra',
    'MateriaOne',
    'PrimeLink',
    'MateriaMaster',
    'PrimaLife',
    'MateriaBrite',
    'PrimeSource',
    'MateriaGenius',

    'PrimaSync',
    'MateriaWorks',
    'PrimeDirect',
    'MateriaTouch',
    'PrimaNow',
    'MateriaSmart',
    'PrimeWay Solutions',
    'MateriaGo',
    'PrimaConnect',
    'MateriaSwift'
]

ruta = r'C:\Proyecto IN\Datos\INEGI'
archivo_csv = 'empresa_prod.csv'

# Verifica si la ruta existe, de lo contrario, la crea
if not os.path.exists(ruta):
    os.makedirs(ruta)

# Escribe el archivo CSV
with open(os.path.join(ruta, archivo_csv), mode='w', newline='', encoding='utf-8') as f:
    writer = csv.writer(f)
    writer.writerow(['id', 'Empresa', 'id_prod'])

    for indx, empresa in enumerate(empresas):
        num_aleatorio = random.randint(1, 12)
        writer.writerow([indx, empresa, num_aleatorio])

