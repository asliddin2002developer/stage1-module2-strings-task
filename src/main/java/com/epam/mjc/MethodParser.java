package com.epam.mjc;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     *      1. access modifier - optional, followed by space: ' '
     *      2. return type - followed by space: ' '
     *      3. method name
     *      4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     *      accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     *      private void log(String value)
     *      Vector3 distort(int x, int y, int z, float magnitude)
     *      public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        MethodSignature methodSignature = new MethodSignature();
        StringTokenizer stringTokenizer = new StringTokenizer(signatureString, " ,()");
        ArrayList<String> accessModifiersList = new ArrayList<>();
        accessModifiersList.add("private");
        accessModifiersList.add("public");
        accessModifiersList.add("protected");
        List<String> typeAndName = new ArrayList<>();
        int i = 1;
        while (stringTokenizer.hasMoreTokens()){
            String curElem = stringTokenizer.nextToken();
            if (i==1 && accessModifiersList.contains(curElem)){
                methodSignature.setAccessModifier(curElem);
            } else if ((i==1 || i == 2) && methodSignature.getReturnType() == null) {
                methodSignature.setReturnType(curElem);
            } else if ((i==2 || i == 3) && methodSignature.getMethodName() == null){
                methodSignature.setMethodName(curElem);
            }else {
                typeAndName.add(curElem);
            }
            i++;
        }
        List<MethodSignature.Argument> argumentsList = new ArrayList<>();
        for (int j=0; j<typeAndName.size(); j+=2){
            MethodSignature.Argument argument = new MethodSignature.Argument(typeAndName.get(j), typeAndName.get(j+1));
            argumentsList.add(argument);
        }
        methodSignature.setArguments(argumentsList);

        return methodSignature;
    }
}
