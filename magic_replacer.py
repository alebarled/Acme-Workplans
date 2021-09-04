'''
MAGIC REPLACER 1.0
A little hard-coded. Have no time for mercy.
Any suggestions contact the author (AB)

'''

from os import listdir, remove,system,rename,chdir, path
from os.path import isfile, join
from time import sleep

def strreplace(string,values):
	for key in values.keys():
		string = string.replace(key,values[key])

	return string


def replacer(route,values):
	with open(route,'r',encoding='utf-8') as s:
		content = s.read()
	content = str(content)
	remove(route)
	for key in values.keys():
		content = content.replace(key,values[key])
		route = route.replace(key,values[key])
	with open(route,'w+',encoding='utf-8') as s:
		s.writelines(content)

def filesfinder(route):
	if isfile(route):
		extension = route.split('/')[-1].split('.')[-1]
		if extension in extensions:
			return [route]
		else:
			return []
	else:
		lista = []
		chdir = path.dirname(path.realpath(__file__))
		for x in listdir(route):
			try:
				if route != './':
					rename(route+'/'+x,route+'/'+strreplace(x,values))
					print(route+'/'+strreplace(x,values))
				else:
					rename(route+x,route+strreplace(x,values))
					print(route+strreplace(x,values))
			except:
				pass

		for x in listdir(route):
			if route != './':
				lista.extend(filesfinder(route+'/'+x))
			else:
				lista.extend(filesfinder(route+x))
		return lista


if __name__ == '__main__':
	'''
	reading config file
	'''
	section = 0
	values = dict()
	extensions = []
	with open('./config.txt','r',encoding="utf-8") as c:
		for line in c.readlines():
			line = line.replace('\n','')
			if '#' in line:
				section+=1
			else:
				if section == 1:
					values[line.split(',')[0]] = line.split(',')[1]
				else:
					extensions.append(line)

	files = filesfinder('./')
	system('cls')
	print('****************************')
	print('M A G I C    R E P L A C E R')
	print('****************************')
	print('Tool for replace all files names and content')
	print('Check config file for more info')
	sleep(3)


	for f in enumerate(files):
		system('cls')
		print(str(round((f[0]+1)/len(files)*100,2)),'%')
		replacer(f[1],values)
	print('Done!')