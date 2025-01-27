package bean.transform.unittest;

import bean.transform.unittest.jmhtest.BeanTo;
import com.shzz.common.tool.bean.transform.BeanTransform;
import com.shzz.common.tool.bean.transform.asm.TransformUtilGenerate;
import bean.transform.unittest.entity.*;
import com.alibaba.fastjson.JSON;
import net.sf.cglib.beans.BeanCopier;
import org.junit.Test;

import java.util.*;

/**
 * @Classname UnitTest
 * @Description TODO
 * @Date 2022/1/16 11:21
 * @Created by wen wang
 */
public class UnitTest {


    BeanCopier copier = BeanCopier.create(CopyFrom.class, CopyTo.class, false);

    BeanTransform beanTransFormsHandler = null;


    public CopyFrom createCopyFrom() {
        CopyFrom from = new CopyFrom();

        from.setCarDirection(2);
        from.setDistrict("xxx");
        from.setDivider(3);
        from.setFfs((float) 30);
        from.setGridId(4);
        from.setIntersectionFrom("A INTERSECTION");
        from.setIntersectionTo("B INTERSECTION");
        from.setLaneNum(300);
        from.setNodeFrom("A");
        from.setNodeTo("B");
        from.setPoi(null);
        from.setLaneWidth(3.5);
        from.setRoadData("32.555,106.789");
        from.setRoadId("301");
        from.setRoadDirection(2);
        from.setRoadLength((float) 450);
        from.setRoadName("road A");
        from.setRoadNameFrom("road from");
        from.setRoadNameTo("road to");
        from.setRoadOut("road out");
        from.setRoadType(5);
        from.setThresholdId(1200);
        from.setOtherDistrict(12);

        Inner inner = new Inner();

        inner.setGreenRatio(30);
        inner.setPhaseId("1");
        inner.setPhaseName("phase1");
        inner.setPhaseSeqNo("3");
        inner.setRed(3);
        inner.setYellow(3);

        from.setInner(inner);

        List<ListElement> listElementListSource = new ArrayList<>();

        ListElement listElement1 = new ListElement();
        listElement1.setListElementField1("filed1");
        listElement1.setListElementField2(1001);
        listElement1.setListElementField3(1002);
        listElementListSource.add(listElement1);
        ListElement listElement2 = new ListElement();
        listElement2.setListElementField1("filed1");
        listElement2.setListElementField2(2001);
        listElement2.setListElementField3(2002);
        listElementListSource.add(listElement2);
        from.setListElementList(listElementListSource);
        List<List<ListElement>> nest = new ArrayList<>();
        nest.add(listElementListSource);
        from.setNestList(nest);

        ListElement listElementArrEle = new ListElement();
        listElementArrEle.setListElementField1("filed1_for_array");
        listElementArrEle.setListElementField2(5001);
        listElementArrEle.setListElementField3(5002);
        ListElement[] arr1 = new ListElement[1];
        arr1[0] = listElementArrEle;
        ListElement[][] arr2 = new ListElement[1][1];
        arr2[0] = arr1;
        from.setNestArray(arr2);


        Set<Stack<ListElement>> setStack = new HashSet<>();

        Stack<ListElement> elementStack = new Stack<>();
        elementStack.push(listElementArrEle);
        setStack.add(elementStack);
        from.setNestSetStack(setStack);


        List<List<List<Double>>> threeNestList = new ArrayList<>();
        List<List<Double>> ll = new ArrayList<>();
        List<Double> l = new ArrayList<>();
        l.add(Double.valueOf(520.000));
        ll.add(l);
        threeNestList.add(ll);
        from.setThreeNestList(threeNestList);


        List<List<List<Character>>> threeNestCharacterList = new ArrayList<>();
        List<List<Character>> llCharacterList = new ArrayList<>();
        List<Character> lCharacterList = new ArrayList<>();
        lCharacterList.add(Character.valueOf('2'));
        llCharacterList.add(lCharacterList);
        threeNestCharacterList.add(llCharacterList);
        from.setThreeNestCharacterList(threeNestCharacterList);


        byte byteValue = (Double.valueOf(520.000)).byteValue();

        System.out.println("Double to byteValue=" + byteValue);
        System.out.println("Double to byteValue=" + JSON.toJSONString(byteValue));

        List<List<List<String>>> threeNestStringList = new ArrayList<>();
        List<List<String>> llStrings = new ArrayList<>();
        List<String> lStrings = new ArrayList<>();
        List<String> lStrings2 = new ArrayList<>();
        lStrings.add("20.3");
        lStrings.add("213");
        lStrings.add("214");

        lStrings2.add("125.9");
        llStrings.add(lStrings);
        llStrings.add(lStrings2);
        threeNestStringList.add(llStrings);
        from.setThreeNestStringList(threeNestStringList);

        Inner[][] innerArray = new Inner[2][1];
        List<List<Inner>> innerDoubleList = new ArrayList<>();
        List<Inner> innerList = new ArrayList<>();
        Inner[] inners = new Inner[1];
        Inner innerElement = new Inner();


        char rr = 9;
        Object ob = 9;

        innerElement.setGreenRatio(20);
        innerElement.setPhaseId("1");
        innerElement.setPhaseName("phase2");
        innerElement.setPhaseSeqNo("3");
        innerElement.setRed(3);
        innerElement.setYellow(3);

        innerList.add(innerElement);
        innerList.add(innerElement);
        innerDoubleList.add(innerList);

        List<String> stringList = new ArrayList<>();
        stringList.add("key is Inner Object,share with innerArray[0]");
        Map<Inner, List<String>> mapKeyNotPrimitive = new HashMap<>();
        mapKeyNotPrimitive.put(innerElement, stringList);
        from.setMapKeyNotPrimitive(mapKeyNotPrimitive);

        inners[0] = innerElement;

        innerArray[0] = inners;
        innerArray[1] = inners;
        from.setInnerarray(innerArray);
        from.setInnerDoubleList(innerDoubleList);

        List<Inner[][]> listContainArray = new ArrayList<>();
        listContainArray.add(innerArray);
        from.setListContainArray(listContainArray);
        double d = 8.9;
        from.setDouVar(d);

        // set map value

        Inner mapInnerElement = new Inner();

        mapInnerElement.setGreenRatio(20);
        mapInnerElement.setPhaseId("1");
        mapInnerElement.setPhaseName("phase2 in map field");
        mapInnerElement.setPhaseSeqNo("3");
        mapInnerElement.setRed(3);
        mapInnerElement.setYellow(3);

        Map<String, Map<String, Inner>> twoLayerMap = new HashMap<>();
        Map<String, Inner> twoLayerInnerMap = new HashMap<>();
        twoLayerInnerMap.put("layer2", mapInnerElement);
        twoLayerMap.put("layer1", twoLayerInnerMap);
        from.setTwoLayerMap(twoLayerMap);


        List<Map<String, Inner>> listContainMap = new ArrayList<>();
        List<Map<String, Map<String, Inner>>> listContaintwoLayerMap = new ArrayList<>();
        listContaintwoLayerMap.add(twoLayerMap);
        from.setListContainTwoLayerMap(listContaintwoLayerMap);

        listContainMap.add(twoLayerInnerMap);

        from.setListContainMap(listContainMap);

        Inner mapContainListInnerElement = new Inner();

        mapContainListInnerElement.setGreenRatio(30);
        mapContainListInnerElement.setPhaseId("1");
        mapContainListInnerElement.setPhaseName("phase2 in mapContainList field");
        mapContainListInnerElement.setPhaseSeqNo("3");
        mapContainListInnerElement.setRed(3);
        mapContainListInnerElement.setYellow(3);
        Map<String, List<Inner>> mapContainListField = new HashMap<>();

        List<Inner> mapContainListList = new ArrayList<>();

        mapContainListList.add(mapContainListInnerElement);
        mapContainListField.put("fieldMapwithList", mapContainListList);
        from.setMapContainList(mapContainListField);

        from.setObject(new Object());

        from.setJsonObject(JSON.parseObject(JSON.toJSONString(mapContainListInnerElement)));

        return from;
    }


    public UnitTest() {


        try {
            //  beanTransFormsHandler = TransformUtilGenerate.generate(CopyFrom.class, CopyTo.class, true, true, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    CopyFrom from = createCopyFrom();


    public void testBaseType() throws Exception {
        CopyFrom from = createCopyFrom();
        BeanTransform beanTransFormsBase = TransformUtilGenerate.generate(CopyFrom.class, BaseEntity.class, true, true, null);


    }

    public void testComplexType() throws Exception {
        CopyFrom from = createCopyFrom();
        BeanTransform beanTransFormsComplex = TransformUtilGenerate.generate(CopyFrom.class, ComplexFieldEntity.class, true, true, null);


    }


    @Test
    public void test() throws Exception {


        CopyFrom from = createCopyFrom();
        int times = 1;

        int loop = 1;

//        CopyTo copyTo8 = new CopyTo();
//        org.springframework.beans.BeanUtils.copyProperties(from, copyTo8);


        //   System.out.println(" cglib  BeanTransFormsHandler  springBeanUtils   Copier 转换拷贝bean 对比测试");


        //  BeanUtils.copyProperties(from,new CopyTo());
        //   org.apache.commons.beanutils.BeanUtils.cloneBean(from);

        CopyTo copyTo1 = new CopyTo();
        CopyTo copyTo2 = new CopyTo();
        CopyTo copyTo3 = new CopyTo();
        CopyTo copyTo4 = new CopyTo();
        CopyTo copyTo5 = new CopyTo();


        for (int lo = 0; lo < loop; ++lo) {

            Thread.sleep(100);

            BeanTransform beanTransFormsHandler = TransformUtilGenerate.generate(CopyFrom.class, CopyTo.class, true, true, null);


            long time1 = System.nanoTime();

            for (int j = 0; j < times; ++j) {

                copyTo1 = new CopyTo();
                copier.copy(from, copyTo1, null);

                // System.out.println(JSON.toJSONString(compareTo));
            }
            long time2 = System.nanoTime();
            // System.out.println("from=" + JSON.toJSONString(from));
            // System.out.println(" copyTo1=" + JSON.toJSONString(copyTo1));
            long time3_1 = System.nanoTime();


            for (int K = 0; K < times; ++K) {


                copyTo2 = beanTransFormsHandler.beanTransform(CopyFrom.class,
                        from,
                        CopyTo.class);

                from.getInnerDoubleList().get(0).get(1).setPhaseName("修改phase");

                System.out.println("修改 from =" + JSON.toJSONString(from));
                System.out.println(" copyTo2 =" + JSON.toJSONString(copyTo2));
//
//                  System.out.println("from="+JSON.toJSONString(from));
//                 System.out.println("copyTo2="+JSON.toJSONString(copyTo2));


            }


            long time3 = System.nanoTime();
            //   System.out.println("from=" + JSON.toJSONString(from));
            //   System.out.println("beanTransFormsHandler  copyTo2=" + JSON.toJSONString(copyTo2));
            // System.out.println("copyTo2.getIntThreeDems()[0][0][0]=="+copyTo2.getIntThreeDems()[0][0][0]);


            ComplexTypeTransformManual complexTypeTransformManual = new ComplexTypeTransformManual();
            long time4_1 = System.nanoTime();
            for (int K = 0; K < times; ++K) {

                //  copyTo3 = complexTypeTransformManual.beanTransforms(from);


                //  org.springframework.beans.BeanUtils.copyProperties(from, copyTo3);
                //   org.springframework.beans.BeanUtils.copyProperties(from,copyTo3);


            }

            long time4 = System.nanoTime();
            //  System.out.println("from=" + JSON.toJSONString(from));
            //   System.out.println("manual  copyTo3=" + JSON.toJSONString(copyTo3));
            for (int K = 0; K < times; ++K) {

                // copyTo3 = new CopyTo();
                //  org.apache.commons.beanutils.BeanUtils.copyProperties(from,copyTo3);

                // 深度克隆拷贝
                // CopyFrom from1= (CopyFrom) org.apache.commons.beanutils.BeanUtils.cloneBean(from);


            }

            long time5 = System.nanoTime();

            //  MapperStructConvert mapperStructConvert=  MapperStructConvert.INSTANCE;
            long time6_1 = System.nanoTime();

            for (int K = 0; K < times; ++K) {

                //    copyTo5 =mapperStructConvert.transform(from);


            }

            long time6 = System.nanoTime();

//            System.out.println("MapperStructConvert:" + JSON.toJSONString(copyTo5));
//
//
//            System.out.println(" 转换拷贝bean " + times + "次 总时间（毫秒）, " + ",cglib vs BeanTransFormsHandler vs manual " + "： "
//                    + (time2 - time1) / 1000000 + "   "
//                    + (time3 - time3_1) / 1000000 + "  " +
//                    (time4 - time4_1) / 1000000 + "  ");


        }


    }


}
