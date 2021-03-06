package com.enjoylearning.mybatis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jws.soap.SOAPBinding.Use;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.Reflector;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.BeanWrapper;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;

import com.enjoylearning.mybatis.entity.EmailSexBean;
import com.enjoylearning.mybatis.entity.THealthReportFemale;
import com.enjoylearning.mybatis.entity.TJobHistory;
import com.enjoylearning.mybatis.entity.TPosition;
import com.enjoylearning.mybatis.entity.TUser;
import com.enjoylearning.mybatis.mapper.THealthReportFemaleMapper;
import com.enjoylearning.mybatis.mapper.TJobHistoryAnnoMapper;
import com.enjoylearning.mybatis.mapper.TUserMapper;
import com.enjoylearning.mybatis.mapper.TUserTestMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

public class MybatisDemo {
	

	private SqlSessionFactory sqlSessionFactory;
	
	@Before
	public void init() throws IOException {
		//--------------------????????????---------------------------
	    // 1.??????mybatis???????????????SqlSessionFactory
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// 1.??????mybatis???????????????SqlSessionFactory
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		inputStream.close();
	}
	
	@Test
	// ????????????
	public void quickStart() throws IOException {
		//--------------------????????????---------------------------
		// 2.??????sqlSession	
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.????????????mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
		
		//--------------------????????????---------------------------
		// 4.???????????????????????????????????????
		TUser user = mapper.selectByPrimaryKey(2);
		System.out.println(user);
		
		System.out.println("----------------------------------");
		
		// 5.???????????????????????????????????????
//		List<TUser> users = mapper.selectAll();
//		for (TUser tUser : users) {
//			System.out.println(tUser);
//		}
	}
	
	@Test
	// ibatis???????????? ????????????
	public void originalOperation() throws IOException {
		// 2.??????sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.?????????????????????????????????
		TUser user = sqlSession.selectOne("com.enjoylearning.mybatis.mapper.TUserMapper.selectByPrimaryKey", 2);
		System.out.println(user.toString());
	}
	
	
	@Test
	//????????????resultType
	public void testAutoMapping() throws IOException {
		// 2.??????sqlSession	
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.????????????mapper
		TUserTestMapper mapper = sqlSession.getMapper(TUserTestMapper.class);
		// 4.???????????????????????????????????????
		
		List<TUser> users = mapper.selectAll();
		for (TUser tUser : users) {
			System.out.println(tUser);
		}
		
	}
	
	@Test
	//????????????resultMap
	public void testResultMap() throws IOException {
		//--------------------????????????---------------------------
		// 2.??????sqlSession	
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.????????????mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
		
		//--------------------????????????---------------------------

		// 4.???????????????????????????????????????
		List<TUser> users = mapper.selectTestResultMap();
		for (TUser tUser : users) {
			System.out.println(tUser.getUserName());
			System.out.println(tUser.getPosition().getPostName());
		}
		

	}
	

	// ???????????????
	@Test
	public void testManyParamQuery() {
		// 2.??????sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.????????????mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);

		String email = "qq.com";
		Byte sex = 1;

		// ?????????????????????map
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("email", email);
//		params.put("sex", sex);
//		List<TUser> list1 = mapper.selectByEmailAndSex1(params);
//		System.out.println(list1.size());

		// ?????????????????????????????????
		Page<TUser> startPage = PageHelper.startPage(2, 3);
		List<TUser> list2 = mapper.selectByEmailAndSex2(email, sex);
		System.out.println(list2.size());
//		return startPage;

		// ????????????????????????
//		EmailSexBean esb = new EmailSexBean();
//		esb.setEmail(email);
//		esb.setSex(sex);
//		List<TUser> list3 = mapper.selectByEmailAndSex3(esb);
//		System.out.println(list3.size());
	}
	
	

	@Test
	// ??????????????????????????????id
	public void testInsertGenerateId1() throws IOException {
		// 2.??????sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.????????????mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
		// 4.?????????????????????????????????
		TUser user1 = new TUser();
		user1.setUserName("test1");
		user1.setRealName("realname1");
		user1.setEmail("myemail1");
		mapper.insert1(user1);
		sqlSession.commit();
		System.out.println(user1.getId());
	}

	@Test
	// ??????????????????????????????id
	public void testInsertGenerateId2() throws IOException {
		// 2.??????sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.????????????mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
		// 4.?????????????????????????????????
		TUser user2 = new TUser();
		user2.setUserName("test2");
		user2.setRealName("realname2");
		user2.setEmail("myemai2l");
		mapper.insert2(user2);
		sqlSession.commit();
		System.out.println(user2.getId());
	}




	@Test
	// ??????#?????????$????????????(??????sql ??????)
	public void testSymbol() {
		// 2.??????sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.????????????mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
		
		String inCol = "id, userName, realName, sex, mobile, email, note";
		String tableName = "t_user";
//		String userName = "lison";
		String userName = "'xxx' or 1=1";
		String orderStr = "sex,userName";
		
		List<TUser> list = mapper.selectBySymbol(tableName, inCol, orderStr, userName);
		System.out.println(list.size());
		
	}
	
	
	//--------------------------------??????sql---------------------------------------
	
	@Test
	// if??????select?????????where??????
	public void testSelectIfOper() {
		// 2.??????sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.????????????mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
		
		String email = "qq.com";
		Byte sex = 1;
//		List<TUser> list = mapper.selectIfOper(null, null);
		List<TUser> list = mapper.selectIfandWhereOper(email, null);
//		List<TUser> list = mapper.selectChooseOper(email , sex);
		
		System.out.println(list.size());
		
	}

	@Test
	// if??????update?????????set??????
	public void testUpdateIfOper() {
		// 2.??????sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession(false);
		// 3.????????????mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
		
		TUser user = new TUser();
		user.setId(3);
		user.setUserName("cindy");
		user.setRealName("?????????");
		user.setEmail("xxoo@163.com");
		user.setMobile("18695988747");
//		user.setNote("cindy's note");
		user.setSex((byte) 2);
//		System.out.println(mapper.updateIfOper(user));
		System.out.println(mapper.updateIfAndSetOper(user));
		sqlSession.commit();
		
	}
	
	
	@Test
	// if??????insert?????????trim??????
	public void testInsertIfOper() {
		// 2.??????sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.????????????mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
		
		TUser user = new TUser();
		user.setUserName("mark");
		user.setRealName("??????");
		user.setEmail("xxoo@163.com");
		user.setMobile("18695988747");
		user.setNote("mark's note");
		user.setSex((byte) 1);
		System.out.println(mapper.insertIfOper(user));
//		System.out.println(mapper.insertSelective(user));
	}
	
	
	
	@Test
	// Foreach??????in??????
	public void testForeach4In() {
		// 2.??????sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.????????????mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
		
		String[] names = new String[]{"lison","james"};
		List<TUser> users = mapper.selectForeach4In(names);
		
		for (TUser tUser : users) {
			System.out.println(tUser.getUserName());
		}
		
		System.out.println(users.size());
	}
	
	
	@Test
	// Foreach??????????????????
	public void testForeach4Insert() {
		// 2.??????sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.????????????mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
		
		TUser user1 = new TUser();
		user1.setUserName("king");
		user1.setRealName("?????????");
		user1.setEmail("li@qq.com");
		user1.setMobile("18754548787");
		user1.setNote("king's note");
		user1.setSex((byte)1);
		TUser user2 = new TUser();
		user2.setUserName("deer");
		user2.setRealName("?????????");
		user2.setEmail("chen@qq.com");
		user2.setMobile("18723138787");
		user2.setNote("deer's note");
		user2.setSex((byte)1);
		
		
		int i = mapper.insertForeach4Batch(Arrays.asList(user1,user2));
		System.out.println("------??????????????????????????????????????????insert????????????--------");
		System.out.println(user1.getId());
		System.out.println(user2.getId());
		
	}
	
	@Test
	// ????????????
	public void testBatchExcutor() {
		// 2.??????sqlSession
//		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, true);
		// 3.????????????mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
		
		TUser user = new TUser();
		user.setUserName("mark");
		user.setRealName("??????");
		user.setEmail("xxoo@163.com");
		user.setMobile("18695988747");
		user.setNote("mark's note");
		user.setSex((byte) 1);
		TPosition positon1 = new TPosition();
		positon1.setId(1);
		user.setPosition(positon1);
		System.out.println(mapper.insertSelective(user));
		
		TUser user1 = new TUser();
		user1.setId(3);
		user1.setUserName("cindy");
		user1.setRealName("?????????");
		user1.setEmail("xxoo@163.com");
		user1.setMobile("18695988747");
		user1.setNote("cindy's note");
		user1.setSex((byte) 2);
		user.setPosition(positon1);
		System.out.println(mapper.updateIfAndSetOper(user1));
		
		sqlSession.commit();
		System.out.println("----------------");
		System.out.println(user.getId());
		System.out.println(user1.getId());

	}
	
	
	
	@Test
	public void mybatisGeneratorTest() throws FileNotFoundException{
		List<String> warnings = new ArrayList<String>(); //????????????list
        boolean overwrite = true;
        String genCfg = "generatorConfig.xml";  
        File configFile = new File(getClass().getClassLoader().getResource(genCfg).getFile());
        ConfigurationParser cp = new ConfigurationParser(warnings);  
        Configuration config = null;  
        try {  
            config = cp.parseConfiguration(configFile);//??????????????????
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (XMLParserException e) {  
            e.printStackTrace();  
        }  
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);  
        MyBatisGenerator myBatisGenerator = null;  
        try {  
            myBatisGenerator = new MyBatisGenerator(config, callback, warnings);//??????????????????  
        } catch (InvalidConfigurationException e) {  
            e.printStackTrace();  
        }  
        try {  
            myBatisGenerator.generate(null); //????????????bean?????????mapper?????? ???xml
        } catch (SQLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
    }

	
	

	//------------------------------??????sql??????----------------------------------------
	
	
	//----------------???????????????????????????????????????---------------------
	@Test
	public void reflectionTest(){
		
//		//????????????????????????
//		ObjectFactory objectFactory = new DefaultObjectFactory();
//		TUser user = objectFactory.create(TUser.class);
//		ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();
//		ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
//		MetaObject metaObject = MetaObject.forObject(user, objectFactory, objectWrapperFactory, reflectorFactory);
//
//		
//		//??????Reflector??????????????????
//		Reflector findForClass = reflectorFactory.findForClass(TUser.class);
//		Constructor<?> defaultConstructor = findForClass.getDefaultConstructor();
//		String[] getablePropertyNames = findForClass.getGetablePropertyNames();
//		String[] setablePropertyNames = findForClass.getSetablePropertyNames();
//		System.out.println(defaultConstructor.getName());
//		System.out.println(Arrays.toString(getablePropertyNames));
//		System.out.println(Arrays.toString(setablePropertyNames));
//		
//		
//	    //??????ObjectWrapper?????????????????????????????????????????????????????????
//		TUser userTemp = new TUser();
//		ObjectWrapper wrapperForUser = new BeanWrapper(metaObject, userTemp);
//		String[] getterNames = wrapperForUser.getGetterNames();
//		String[] setterNames = wrapperForUser.getSetterNames();
//		System.out.println(Arrays.toString(getterNames));
//		System.out.println(Arrays.toString(setterNames));
//		
//		PropertyTokenizer prop = new PropertyTokenizer("userName");
//		wrapperForUser.set(prop, "lison");
//		System.out.println(userTemp);
		
//---------------------------------------------------------------
		//???????????????????????????????????????
		ObjectFactory objectFactory = new DefaultObjectFactory();
		TUser user = objectFactory.create(TUser.class);
//		ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();
//		ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
//		MetaObject metaObject = MetaObject.forObject(user, objectFactory, objectWrapperFactory, reflectorFactory);
		MetaObject metaObject = SystemMetaObject.forObject(user);
		
		
		 

		
		//1.??????????????????????????????
		Map<String, Object> dbResult = new HashMap<>();
		dbResult.put("id", 1);
		dbResult.put("userName", "lison");
		dbResult.put("realName", "?????????");
		TPosition tp = new TPosition();
		tp.setId(1);
		dbResult.put("position_id", tp);
		//2.??????????????????
		Map<String, String> mapper = new HashMap<String, String>();
		mapper.put("id", "id");
		mapper.put("userName", "userName");
		mapper.put("realName", "realName");
		mapper.put("position", "position_id");
		
		//3.??????????????????????????????????????????pojo
		
		//??????BeanWrapper,????????????????????????????????????????????????????????????
		BeanWrapper objectWrapper = (BeanWrapper) metaObject.getObjectWrapper();
		
		Set<Entry<String, String>> entrySet = mapper.entrySet();
		//??????????????????
		for (Entry<String, String> colInfo : entrySet) {
			String propName = colInfo.getKey();//??????pojo???????????????
			Object propValue = dbResult.get(colInfo.getValue());//????????????????????????????????????????????????
			PropertyTokenizer proTokenizer = new PropertyTokenizer(propName);
			objectWrapper.set(proTokenizer, propValue);//???????????????????????????pojo????????????
		}
		System.out.println(metaObject.getOriginalObject());
		
	}




/*	@Test
	// ????????????
	public void testAnno() {
		// 2.??????sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.????????????mapper
		TJobHistoryAnnoMapper mapper = sqlSession.getMapper(TJobHistoryAnnoMapper.class);
		
		List<TJobHistory> list = mapper.selectByUserId(1);
		System.out.println(list.size());
		
		List<TJobHistory> listAll = mapper.selectAll();
		System.out.println(listAll.size());
		
		TJobHistory job = new TJobHistory();
		job.setTitle("????????????");
		job.setUserId(1);
		job.setCompName("??????");
		job.setYears(3);
		
		mapper.insert(job);
		System.out.println(job.getId());
	}
*/	
	
	
	
	
	


}
