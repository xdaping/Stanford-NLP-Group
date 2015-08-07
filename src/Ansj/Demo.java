package Ansj;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Collection;
import java.util.List;

import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.ansj.dic.LearnTool;
import org.ansj.domain.Nature;
import org.ansj.domain.Term;
import org.ansj.library.UserDefineLibrary;
import org.ansj.recognition.NatureRecognition;
import org.ansj.splitWord.analysis.BaseAnalysis;
import org.ansj.splitWord.analysis.IndexAnalysis;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.nlpcn.commons.lang.tire.GetWord;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.library.Library;

public class Demo {

	public static void main(String[] args) {
		semgmenter();//各种分词
        
        //dynamicWordDemo();//用户自定义词典

        //keyWordCompuerDemo();//关键词抽取
        
        //NatureDemo();//词性标注
        
        //treeSplit();//
        
        //learnToolDemo();//新词发现
	}
	
	
	
    private static void semgmenter() {
    	List<Term> parse = null;
    	
    	//基本分词:用户自定义词典	数字识别	人名识别	机构名识别	新词发现
    	//           Χ	      √	      Χ	      Χ	      Χ
    	parse = BaseAnalysis.parse("让战士们过一个欢乐祥和的新春佳节。");
        System.out.println(parse);
        
        //精准分词: 用户自定义词典	数字识别	人名识别	机构名识别	新词发现
        //             √	  √	  	 √	   	  Χ		 Χ  
        parse = ToAnalysis.parse("让战士们过一个欢乐祥和的新春佳节。");
        System.out.println(parse);
        
        //NLP分词:用户自定义词典	数字识别	人名识别	机构名识别	新词发现
        //			√		  √		  √		  √	  	  √
        parse = NlpAnalysis.parse("洁面仪配合洁面深层清洁毛孔 清洁鼻孔面膜碎觉使劲挤才能出一点点皱纹 脸颊毛孔修复的看不见啦 草莓鼻历史遗留问题没辙 脸和脖子差不多颜色的皮肤才是健康的 长期使用安全健康的比同龄人显小五到十岁 28岁的妹子看看你们的鱼尾纹");
        System.out.println(parse);
        
        //面向索引分词：用户自定义词典	数字识别	人名识别	机构名识别	新词发现
        //			√		 √		√		Χ		Χ
        parse = IndexAnalysis.parse("主副食品");
        System.out.println(parse);
	}



	public static void NatureDemo() {
    	try{
	        List<Term> terms = ToAnalysis.parse("Ansj中文分词是一个真正的ict的实现.并且加入了自己的一些数据结构和算法的分词.实现了高效率和高准确率的完美结合!");
	        new NatureRecognition(terms).recognition(); //词性标注
	        System.out.println(terms);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
	
	public static void keyWordCompuerDemo() {
	    KeyWordComputer kwc = new KeyWordComputer(5);
	    String title = "维基解密否认斯诺登接受委内瑞拉庇护";
	    String content = "有俄罗斯国会议员，9号在社交网站推特表示，美国中情局前雇员斯诺登，已经接受委内瑞拉的庇护，不过推文在发布几分钟后随即删除。俄罗斯当局拒绝发表评论，而一直协助斯诺登的维基解密否认他将投靠委内瑞拉。　　俄罗斯国会国际事务委员会主席普什科夫，在个人推特率先披露斯诺登已接受委内瑞拉的庇护建议，令外界以为斯诺登的动向终于有新进展。　　不过推文在几分钟内旋即被删除，普什科夫澄清他是看到俄罗斯国营电视台的新闻才这样说，而电视台已经作出否认，称普什科夫是误解了新闻内容。　　委内瑞拉驻莫斯科大使馆、俄罗斯总统府发言人、以及外交部都拒绝发表评论。而维基解密就否认斯诺登已正式接受委内瑞拉的庇护，说会在适当时间公布有关决定。　　斯诺登相信目前还在莫斯科谢列梅捷沃机场，已滞留两个多星期。他早前向约20个国家提交庇护申请，委内瑞拉、尼加拉瓜和玻利维亚，先后表示答应，不过斯诺登还没作出决定。　　而另一场外交风波，玻利维亚总统莫拉莱斯的专机上星期被欧洲多国以怀疑斯诺登在机上为由拒绝过境事件，涉事国家之一的西班牙突然转口风，外长马加略]号表示愿意就任何误解致歉，但强调当时当局没有关闭领空或不许专机降落。";
	        Collection<Keyword> result = kwc.computeArticleTfidf(title, content);
	            System.out.println(result);
	    
	}
	 
    public static void dynamicWordDemo() {
        // 增加新词,中间按照'\t'隔开
        UserDefineLibrary.insertWord("碎觉", "userDefine", 1000);
        List<Term> terms = ToAnalysis.parse("我要碎觉吊丝要小心！城西嘉南公寓 ");
        System.out.println("增加新词例子:" + terms);
        // 删除词语,只能删除.用户自定义的词典.
        UserDefineLibrary.removeWord("碎觉");
        terms = ToAnalysis.parse("我要碎觉吊丝要小心！城西嘉南公寓 ");
        System.out.println("删除用户自定义词典例子:" + terms);
    }
    
    public static void treeSplit(){
    	try{
	    	/**
	         * 词典的构造.一行一个词后面是参数.可以从文件读取.可以是read流.
	         */
	        String dic = "中国\t1\tzg\n人名\t2\n中国人民\t4\n人民\t3\n孙健\t5\nCSDN\t6\njava\t7\njava学习\t10\n";	      
	        
	        Forest forest = Library.makeForest(new BufferedReader(new StringReader(dic)));
	        
	        /**
	         * 删除一个单词
	         */
	        Library.removeWord(forest, "中国");
	        /**
	         * 增加一个新词
	         */
	        Library.insertWord(forest, "中国人");
	        String content = "中国人名识别是中国人民的一个骄傲.孙健人民在CSDN中学到了很多最早iteye是java学习笔记叫javaeye但是java123只是一部分";
	        GetWord udg = forest.getWord(content);
	
	        String temp = null;
	        while ((temp = udg.getFrontWords()) != null)
	            System.out.println(temp + "\t\t" + udg.getParam(1) + "\t\t" + udg.getParam(2));
        }catch(Exception e){
    		e.printStackTrace();
    	}
     }
    
    
    /**
     * 新词发现工具
     */
    public static void learnToolDemo() {
        //构建一个新词学习的工具类。这个对象。保存了所有分词中出现的新词。出现次数越多。相对权重越大。
        LearnTool learnTool = new LearnTool() ;

        //进行词语分词。也就是nlp方式分词，这里可以分多篇文章
        NlpAnalysis.parse("说过，社交软件也是打着沟通的平台，让无数寂寞男女有了肉体与精神的寄托。", learnTool) ;
        NlpAnalysis.parse("其实可以打着这个需求点去运作的互联网公司不应只是社交类软件与可穿戴设备，还有携程网，去哪儿网等等，订房订酒店多好的寓意", learnTool) ;
        NlpAnalysis.parse("张艺谋的卡宴，马明哲的戏",learnTool) ;

        //取得学习到的topn新词,返回前10个。这里如果设置为0则返回全部
        System.out.println(learnTool.getTopTree(10));

        //只取得词性为Nature.NR的新词
        System.out.println(learnTool.getTopTree(10,Nature.NR));        
    }

}
