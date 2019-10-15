# Gama
<b>GAMA</b> (Graphematic and morphological analysis) – прослойка, позволяющая не заморачиваться подключением парсера и морфологическим разбором. На вход принимает String – на выходе распарсенные слова с их морфологическими характеристиками (не весь функционал протестирован).<br>
#### Пример
```
Gama gama = new Gama();
gama.init();
RefSentenceList sentenceList = gama.getMorphParagraph("Осенний марафон -"
		+ " стало ясно, что будет с российской валютой. Справедливый курс,"
		+ " по мнению аналитиков, — на уровне 65-66.");
System.out.println(sentenceList);
```
#### Вывод
```
RefSentenceList:
[
	RefBearingPhraseList:
	[	
		RefWordList:
		[
			RefOmoFormList - осенний : [{isInit=true,hash=51957968,осенний,ToS=18,morf=100}, {isInit=false,hash=523831504,осенний,ToS=18,morf=551}], 
			RefOmoFormList - марафон : [{isInit=true,hash=38248644,марафон,ToS=17,morf=103}, {isInit=false,hash=415466948,марафон,ToS=17,morf=551}], 
			RefOmoFormList: null, 
			RefOmoFormList - стал : [{isInit=false,hash=758268027,стало,ToS=20,morf=669740}], 
			RefOmoFormList - ясно : [{isInit=true,hash=96318212,ясно,ToS=9,morf=8388608}, {isInit=false,hash=859065348,ясно,ToS=19,morf=4140}]
		], 	
		RefWordList:[
			RefOmoFormList - что : [{isInit=true,hash=93560832,что,ToS=13,morf=0}, {isInit=true,hash=93560832,что,ToS=30,morf=108}, {isInit=false,hash=837844480,что,ToS=30,morf=556}, {isInit=true,hash=93560832,что,ToS=9,morf=1048576}, {isInit=true,hash=93560832,что,ToS=14,morf=0}], 
			RefOmoFormList - есть : [{isInit=false,hash=200326010,будет,ToS=20,morf=785440}], 
			RefOmoFormList - с : [{isInit=true,hash=77266944,с,ToS=12,morf=0}, {isInit=true,hash=77266944,с,ToS=17,morf=16777323}, {isInit=false,hash=860471040,с,ToS=17,morf=16777451}, {isInit=false,hash=860471040,с,ToS=17,morf=16777451}, {isInit=false,hash=860471040,с,ToS=17,morf=16777835}, {isInit=false,hash=860471040,с,ToS=17,morf=16777579}, {isInit=false,hash=860471040,с,ToS=17,morf=16777707}, {isInit=false,hash=860471040,с,ToS=17,morf=16777339}, {isInit=false,hash=860471040,с,ToS=17,morf=16777467}, {isInit=false,hash=860471040,с,ToS=17,morf=16777467}, {isInit=false,hash=860471040,с,ToS=17,morf=16777851}, {isInit=false,hash=860471040,с,ToS=17,morf=16777595}, {isInit=false,hash=860471040,с,ToS=17,morf=16777723}, {isInit=true,hash=77266944,с,ToS=17,morf=16777327}, {isInit=false,hash=860471040,с,ToS=17,morf=16777455}, {isInit=false,hash=860471040,с,ToS=17,morf=16777455}, {isInit=false,hash=860471040,с,ToS=17,morf=16777839}, {isInit=false,hash=860471040,с,ToS=17,morf=16777583}, {isInit=false,hash=860471040,с,ToS=17,morf=16777711}, {isInit=false,hash=860471040,с,ToS=17,morf=16777343}, {isInit=false,hash=860471040,с,ToS=17,morf=16777471}, {isInit=false,hash=860471040,с,ToS=17,morf=16777471}, {isInit=false,hash=860471040,с,ToS=17,morf=16777855}, {isInit=false,hash=860471040,с,ToS=17,morf=16777599}, {isInit=false,hash=860471040,с,ToS=17,morf=16777727}, {isInit=true,hash=77266944,с,ToS=17,morf=-2030043038}, {isInit=false,hash=860471040,с,ToS=17,morf=-2030042910}, {isInit=false,hash=860471040,с,ToS=17,morf=-2030042910}, {isInit=false,hash=860471040,с,ToS=17,morf=-2030042526}, {isInit=false,hash=860471040,с,ToS=17,morf=-2030042782}, {isInit=false,hash=860471040,с,ToS=17,morf=-2030042654}, {isInit=true,hash=77266944,с,ToS=17,morf=-2030043038}, {isInit=false,hash=860471040,с,ToS=17,morf=-2030042910}, {isInit=false,hash=860471040,с,ToS=17,morf=-2030042910}, {isInit=false,hash=860471040,с,ToS=17,morf=-2030042526}, {isInit=false,hash=860471040,с,ToS=17,morf=-2030042782}, {isInit=false,hash=860471040,с,ToS=17,morf=-2030042654}, {isInit=true,hash=77266944,с,ToS=14,morf=0}], 
			RefOmoFormList - российский : [{isInit=false,hash=709040926,российской,ToS=18,morf=168}, {isInit=false,hash=709040926,российской,ToS=18,morf=232}, {isInit=false,hash=709040926,российской,ToS=18,morf=360}, {isInit=false,hash=709040926,российской,ToS=18,morf=488}], 
			RefOmoFormList - валюта : [{isInit=false,hash=202458017,валютой,ToS=17,morf=363}]
		]
	], 
	RefBearingPhraseList:
	[	
		RefWordList:
		[	
			RefOmoFormList - справедливый : [{isInit=true,hash=82653850,справедливый,ToS=18,morf=4196}, {isInit=false,hash=753780634,справедливый,ToS=18,morf=4647}], 
			RefOmoFormList - курс : [{isInit=true,hash=35909123,курс,ToS=17,morf=103}, {isInit=false,hash=397506307,курс,ToS=17,morf=551}]
		], 	
		RefWordList:
		[
			RefOmoFormList - по : [{isInit=true,hash=59800320,по,ToS=12,morf=0}, {isInit=true,hash=59800320,по,ToS=17,morf=-2130706321}, {isInit=false,hash=860904448,по,ToS=17,morf=-2130706193}, {isInit=false,hash=860904448,по,ToS=17,morf=-2130706193}, {isInit=false,hash=860904448,по,ToS=17,morf=-2130705809}, {isInit=false,hash=860904448,по,ToS=17,morf=-2130706065}, {isInit=false,hash=860904448,по,ToS=17,morf=-2130705937}, {isInit=true,hash=59800320,по,ToS=17,morf=83886178}, {isInit=false,hash=860904448,по,ToS=17,morf=83886306}, {isInit=false,hash=860904448,по,ToS=17,morf=83886306}, {isInit=false,hash=860904448,по,ToS=17,morf=83886690}, {isInit=false,hash=860904448,по,ToS=17,morf=83886434}, {isInit=false,hash=860904448,по,ToS=17,morf=83886562}, {isInit=false,hash=860904448,по,ToS=17,morf=83886194}, {isInit=false,hash=860904448,по,ToS=17,morf=83886322}, {isInit=false,hash=860904448,по,ToS=17,morf=83886322}, {isInit=false,hash=860904448,по,ToS=17,morf=83886706}, {isInit=false,hash=860904448,по,ToS=17,morf=83886450}, {isInit=false,hash=860904448,по,ToS=17,morf=83886578}], 
			RefOmoFormList - мнение : [{isInit=false,hash=427995886,мнению,ToS=17,morf=239}], 
			RefOmoFormList - аналитик : [{isInit=false,hash=157299750,аналитиков,ToS=17,morf=182}, {isInit=false,hash=157299750,аналитиков,ToS=17,morf=566}]
		], 	
		RefWordList:
		[
			RefOmoFormList - на : [{isInit=true,hash=41013504,на,ToS=12,morf=0}, {isInit=true,hash=41013504,на,ToS=15,morf=0}, {isInit=true,hash=41013504,на,ToS=14,morf=0}], 
			RefOmoFormList - уровень : [{isInit=false,hash=805513479,уровне,ToS=17,morf=487}], 
			RefOmoFormList: null
		]
	]

]
```