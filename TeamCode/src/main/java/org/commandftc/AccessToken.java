package org.commandftc;

/**
 * <p>
 * This class is used to emulate the C++ "friend" class features. 
 * Suppose a class (say {@code ClassA}) wants to have a method 
 * (say {@code myMethod}) that only {@code ClassB} can access. Then
 * {@code ClassB} would create, as an inner class of {@code ClassB}, 
 * a subclass of {@code AccessToken} (say {@code ClassBAccessToken}) 
 * with a {@code private} constructor, as follows.
 * </p>
 * 
 * {@code private class ClassBAccessToken {
 *     private ClassBAccessToken() {}
 * }
 * }
 * 
 * Then, {@code myMethod} should have an extra parameter of {@code AccessToken} 
 * and check if the given token is not null. In this way, only {@code ClassB} can
 * access {@code ClassA}'s {@code myMethod}.
 * 
 * @see https://stackoverflow.com/questions/182278/is-there-a-way-to-simulate-the-c-friend-concept-in-java
 */
public abstract class AccessToken {
}