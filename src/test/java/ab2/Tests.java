package ab2;

import ab2.impl.PRUELLERRADLER.FactoryImpl;
import org.junit.jupiter.api.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class Tests {
	private FA n1; // leere Menge
	private FA n2; // epsilon
	private FA n3; // a*
	private FA n4; // {a,b}*
	private FA n5; // {a,b}* | c*
	private FA n6; // {a,b,c}*
	private FA n7; // Bsp 5.1
	private FA n8; // Bsp 5.2
	private FA n9; // irgendwas :)
	private FA n10; // abc
	private FA n11; // (abc)+
	private FA n12; // (abc|ab)+

	private static int pts = 0;

	static Factory factory = new FactoryImpl();

	public static final Set<Character> chars = new HashSet<>();

	private static Set<Character> readChars = new HashSet<>(Arrays.asList('0', '1', 'a', 'b', 'c', 'd', 'e', 'f', '|'));
	private static Set<Character> writeChars = new HashSet<>(Arrays.asList('0', '1', 'a', 'b', 'c', 'd', 'e', 'f'));

	static {
		chars.add('a');
		chars.add('b');
		chars.add('c');
	}

	@BeforeEach
	public void InitializeNFA1() {
		Set<Integer> accept = new TreeSet<>();

		n1 = factory.createFA(1, chars, accept, Collections.emptySet());
	}

	@BeforeEach
	public void InitializeNFA2() {
		Set<Integer> accept = new TreeSet<>();
		accept.add(0);

		Set<FATransition> transitions = new HashSet<>();

		transitions.add(factory.createTransition(1, 2, "a"));
		transitions.add(factory.createTransition(2, 3, "a"));
		transitions.add(factory.createTransition(3, 4, "a"));
		transitions.add(factory.createTransition(4, 0, "a"));

		n2 = factory.createFA(5, chars, accept, transitions);
	}

	@BeforeEach
	public void InitializeNFA3() {
		Set<Integer> accept = new TreeSet<>();
		accept.add(0);

		Set<FATransition> transitions = new HashSet<>();

		transitions.add(factory.createTransition(0, 0, "a"));

		n3 = factory.createFA(1, chars, accept, transitions);
	}

	@BeforeEach
	public void InitializeNFA4() {
		Set<Integer> accept = new TreeSet<>();
		accept.add(0);

		Set<FATransition> transitions = new HashSet<>();

		transitions.add(factory.createTransition(0, 0, "a"));
		transitions.add(factory.createTransition(0, 0, "b"));

		n4 = factory.createFA(5, chars, accept, transitions);
	}

	@BeforeEach
	public void InitializeNFA5() {
		Set<Integer> accept = new TreeSet<>();
		accept.add(0);
		accept.add(1);

		Set<FATransition> transitions = new HashSet<>();

		transitions.add(factory.createTransition(0, 0, "a"));
		transitions.add(factory.createTransition(0, 0, "b"));
		transitions.add(factory.createTransition(1, 1, "c"));
		transitions.add(factory.createTransition(0, 1, ""));

		n5 = factory.createFA(2, chars, accept, transitions);
	}

	@BeforeEach
	public void InitializeNFA6() {
		Set<Integer> accept = new TreeSet<>();
		accept.add(0);
		accept.add(1);

		Set<FATransition> transitions = new HashSet<>();
		transitions.add(factory.createTransition(0, 0, "a"));
		transitions.add(factory.createTransition(0, 0, "b"));
		transitions.add(factory.createTransition(1, 1, "c"));
		transitions.add(factory.createTransition(0, 1, ""));
		transitions.add(factory.createTransition(1, 0, ""));

		n6 = factory.createFA(2, chars, accept, transitions);
	}

	@BeforeEach
	public void InitializeNFA7() {
		Set<Integer> accept = new TreeSet<>();
		accept.add(4);

		Set<FATransition> transitions = new HashSet<>();

		transitions.add(factory.createTransition(0, 1, "b"));
		transitions.add(factory.createTransition(0, 2, "a"));
		transitions.add(factory.createTransition(0, 3, "a"));
		transitions.add(factory.createTransition(1, 2, "b"));
		transitions.add(factory.createTransition(1, 4, "a"));
		transitions.add(factory.createTransition(2, 2, "a"));
		transitions.add(factory.createTransition(2, 4, "b"));
		transitions.add(factory.createTransition(3, 2, "b"));

		n7 = factory.createFA(5, chars, accept, transitions);
	}

	@BeforeEach
	public void InitializeNFA8() {
		Set<Integer> accept = new TreeSet<>();
		accept.add(3);
		accept.add(4);

		Set<FATransition> transitions = new HashSet<>();

		transitions.add(factory.createTransition(0, 1, "a"));
		transitions.add(factory.createTransition(0, 2, "b"));
		transitions.add(factory.createTransition(1, 4, "b"));
		transitions.add(factory.createTransition(2, 2, "b"));
		transitions.add(factory.createTransition(2, 1, "a"));
		transitions.add(factory.createTransition(2, 3, "a"));
		transitions.add(factory.createTransition(4, 2, "b"));
		transitions.add(factory.createTransition(4, 3, "a"));

		transitions.add(factory.createTransition(1, 0, ""));
		transitions.add(factory.createTransition(4, 0, ""));
		transitions.add(factory.createTransition(3, 1, ""));
		transitions.add(factory.createTransition(4, 2, ""));

		n8 = factory.createFA(5, chars, accept, transitions);
	}

	@BeforeEach
	public void InitializeNFA9() {
		Set<Integer> accept = new TreeSet<>();
		accept.add(7);

		Set<FATransition> transitions = new HashSet<>();

		transitions.add(factory.createTransition(5, 6, "a"));
		transitions.add(factory.createTransition(5, 1, "b"));
		transitions.add(factory.createTransition(6, 7, "b"));
		transitions.add(factory.createTransition(6, 3, "a"));
		transitions.add(factory.createTransition(7, 7, "b"));
		transitions.add(factory.createTransition(7, 7, "a"));
		transitions.add(factory.createTransition(3, 4, "b"));
		transitions.add(factory.createTransition(3, 0, "a"));
		transitions.add(factory.createTransition(0, 1, "b"));
		transitions.add(factory.createTransition(0, 0, "a"));
		transitions.add(factory.createTransition(2, 3, "a"));
		transitions.add(factory.createTransition(2, 1, "b"));
		transitions.add(factory.createTransition(1, 2, "a"));
		transitions.add(factory.createTransition(1, 1, "b"));
		transitions.add(factory.createTransition(4, 5, "b"));
		transitions.add(factory.createTransition(4, 2, "a"));
		transitions.add(factory.createTransition(1, 4, "c"));
		transitions.add(factory.createTransition(2, 6, "c"));
		transitions.add(factory.createTransition(5, 3, "c"));
		transitions.add(factory.createTransition(1, 6, ""));
		transitions.add(factory.createTransition(2, 4, ""));
		transitions.add(factory.createTransition(7, 3, ""));

		n9 = factory.createFA(8, chars, accept, transitions);
	}

	@BeforeEach
	public void InitializeNFA10() {
		Set<Integer> accept = new TreeSet<>();
		accept.add(1);

		Set<FATransition> transitions = new HashSet<>();

		transitions.add(factory.createTransition(0, 1, "abc"));

		n10 = factory.createFA(2, chars, accept, transitions);
	}

	@BeforeEach
	public void InitializeNFA11() {
		Set<Integer> accept = new TreeSet<>(Arrays.asList(1));

		Set<FATransition> transitions = new HashSet<>();

		transitions.add(factory.createTransition(0, 1, "abc"));
		transitions.add(factory.createTransition(1, 0, ""));

		n11 = factory.createFA(2, chars, accept, transitions);
	}

	@BeforeEach
	public void InitializeNFA12() {
		Set<Integer> accept = new TreeSet<>(Arrays.asList(1));

		Set<FATransition> transitions = new HashSet<>();

		transitions.add(factory.createTransition(0, 1, "abc"));
		transitions.add(factory.createTransition(0, 1, "ab"));
		transitions.add(factory.createTransition(1, 0, ""));

		n12 = factory.createFA(2, chars, accept, transitions);
	}

	@Test
	void NFA_Language() {
		testLanguageNFA1(n1);
		testLanguageNFA2(n2);
		testLanguageNFA3(n3);
		testLanguageNFA4(n4);
		testLanguageNFA5(n5);
		testLanguageNFA6(n6);
		testLanguageNFA7(n7);
		testLanguageNFA8(n8);
		testLanguageNFA9(n9);
		testLanguageNFA10(n10);
		testLanguageNFA11(n11);
		testLanguageNFA12(n12);

		pts++;
	}

	@Test
	public void NFA_Properties() {
		testPropertiesNFA1(n1);
		testPropertiesNFA2(n2);
		testPropertiesNFA3(n3);
		testPropertiesNFA4(n4);
		testPropertiesNFA5(n5);
		testPropertiesNFA6(n6);
		testPropertiesNFA7(n7);
		testPropertiesNFA8(n8);
		testPropertiesNFA9(n9);
		testPropertiesNFA10(n10);
		testPropertiesNFA11(n11);
		testPropertiesNFA12(n12);

		pts++;
	}

	@Test
	public void NFA_Operations_Union() {
		////////////////////// Vereinigung ///////////////////

		FA n = n1.union(n2);
		assertTrue(n.acceptsEpsilon());
		assertTrue(n.isFinite());
		assertFalse(n.isInfinite());
		assertTrue(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n2.union(n3);
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		//////////////////////////

		n = n3.union(n4);
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n4.union(n5);
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertTrue(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		///////////////////////

		n = n5.union(n6);
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertTrue(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertTrue(n.accepts("abcab"));

		////////////////////////

		n = n6.union(n7);
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertTrue(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertTrue(n.accepts("abcab"));

		////////////////////////

		n = n7.union(n8);
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n8.union(n9);
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n9.union(n10);
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n10.union(n11);
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n10.union(n11);
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n11.union(n12);
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertTrue(n.accepts("abcab"));

		pts++;
	}

	@Test
	public void NFA_Operations_Intersection() {
		////////////////////// Durchschnitt ///////////////////

		FA n = n1.intersection(n2);
		assertTrue(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertTrue(n.isFinite());
		assertFalse(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n2.intersection(n3);
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertTrue(n.isFinite());
		assertFalse(n.isInfinite());
		assertTrue(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		//////////////////////////

		n = n3.intersection(n4);
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n4.intersection(n5);
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		///////////////////////

		n = n5.intersection(n6);
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertTrue(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n6.intersection(n7);
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n7.intersection(n8);
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n8.intersection(n9);
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n9.intersection(n10);
		assertTrue(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertTrue(n.isFinite());
		assertFalse(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n10.intersection(n11);
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertTrue(n.isFinite());
		assertFalse(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n11.intersection(n12);
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		pts++;
	}

	@Test
	public void NFA_Operations_Minus() {

		////////////////////// Ohne ///////////////////

		FA n = n1.minus(n2);
		assertTrue(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertTrue(n.isFinite());
		assertFalse(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n2.minus(n3);
		assertTrue(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertTrue(n.isFinite());
		assertFalse(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		//////////////////////////

		n = n3.minus(n4);
		assertTrue(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertTrue(n.isFinite());
		assertFalse(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n4.minus(n5);
		assertTrue(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertTrue(n.isFinite());
		assertFalse(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		///////////////////////

		n = n5.minus(n6);
		assertTrue(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertTrue(n.isFinite());
		assertFalse(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n6.minus(n7);
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertTrue(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertTrue(n.accepts("abcab"));

		////////////////////////

		n = n7.minus(n8);
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertTrue(n.isFinite());
		assertFalse(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertFalse(n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n8.minus(n9);
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n9.minus(n10);
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n10.minus(n11);
		assertTrue(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertTrue(n.isFinite());
		assertFalse(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n11.minus(n12);
		assertTrue(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertTrue(n.isFinite());
		assertFalse(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));


		pts++;
	}

	@Test
	public void NFA_Operations_Concat() {

		////////////////////// Concat ///////////////////

		FA n = n1.concat(n2);
		assertTrue(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertTrue(n.isFinite());
		assertFalse(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n2.concat(n3);
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		//////////////////////////

		n = n3.concat(n4);
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n4.concat(n5);
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertTrue(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		///////////////////////

		n = n5.concat(n6);
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertTrue(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertTrue(n.accepts("abcab"));

		////////////////////////

		n = n6.concat(n7);
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertTrue(n.accepts("abcab"));

		////////////////////////

		n = n7.concat(n8);
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));

		////////////////////////

		n = n8.concat(n9);
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n9.concat(n10);
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n10.concat(n11);
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n11.concat(n12);
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertTrue(n.accepts("abcab"));

		pts++;
	}

	@Test
	public void NFA_Operations_Star() {
		////////////////////// Stern ///////////////////

		FA n = n1.kleeneStar();
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertTrue(n.isFinite());
		assertFalse(n.isInfinite());
		assertTrue(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n2.kleeneStar();
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertTrue(n.isFinite());
		assertFalse(n.isInfinite());
		assertTrue(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		//////////////////////////

		n = n3.kleeneStar();
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n4.kleeneStar();
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		///////////////////////

		n = n5.kleeneStar();
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertTrue(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertTrue(n.accepts("abcab"));

		////////////////////////

		n = n6.kleeneStar();
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertTrue(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertTrue(n.accepts("abcab"));

		////////////////////////

		n = n7.kleeneStar();
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n8.kleeneStar();
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		//////////////////////

		n = n9.kleeneStar();
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n10.kleeneStar();
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n11.kleeneStar();
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n12.kleeneStar();
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertTrue(n.accepts("abcab"));

		pts++;
	}

	@Test
	public void NFA_Operations_Plus() {
		////////////////////// Plus ///////////////////

		FA n = n1.plus();
		assertTrue(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertTrue(n.isFinite());
		assertFalse(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n2.plus();
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertTrue(n.isFinite());
		assertFalse(n.isInfinite());
		assertTrue(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		//////////////////////////

		n = n3.plus();
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n4.plus();
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		///////////////////////

		n = n5.plus();
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertTrue(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertTrue(n.accepts("abcab"));

		////////////////////////

		n = n6.plus();
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertTrue(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertTrue(n.accepts("abcab"));

		////////////////////////

		n = n7.plus();
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n8.plus();
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		///////////////////////

		n = n9.plus();
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n10.plus();
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n11.plus();
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n12.plus();
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertTrue(n.accepts("abcab"));

		pts++;
	}

	@Test
	public void NFA_Operations_Complement() {
		////////////////////// Complement ///////////////////

		FA n = n1.complement();
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertTrue(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertTrue(n.accepts("abcab"));

		////////////////////////

		n = n2.complement();
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertTrue(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertTrue(n.accepts("abcab"));

		//////////////////////////

		n = n3.complement();
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertTrue(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertTrue(n.accepts("abcab"));

		////////////////////////

		n = n4.complement();
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertTrue(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertTrue(n.accepts("abcab"));

		///////////////////////

		n = n5.complement();
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertTrue(n.accepts("abcab"));

		////////////////////////

		n = n6.complement();
		assertTrue(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertTrue(n.isFinite());
		assertFalse(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertEquals(false, n.equalsPlusAndStar());
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));

		////////////////////////

		n = n7.complement();
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertTrue(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertTrue(n.accepts("abcab"));

		////////////////////////

		n = n8.complement();
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertTrue(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertTrue(n.accepts("abcab"));

		////////////////////////

		n = n9.complement();
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertTrue(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertTrue(n.accepts("abc"));
		assertTrue(n.accepts("abcab"));

		////////////////////////

		n = n10.complement();
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertTrue(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertTrue(n.accepts("abcab"));

		////////////////////////

		n = n11.complement();
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertTrue(n.accepts("aabc"));
		assertTrue(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertTrue(n.accepts("abcab"));

		////////////////////////

		n = n12.complement();
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.equalsPlusAndStar());
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertTrue(n.accepts("aabc"));
		assertFalse(n.accepts("ab"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcab"));


		pts++;
	}

	@Test
	public void NFA_Subset() {

		List<FA> nfas = Arrays.asList(n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12);

		// Jeder Automat ist subset von sich selbst
		for (int i = 0; i < nfas.size(); i++) {
			assertTrue(nfas.get(i).subSetOf(nfas.get(i)));
		}

		// Jeder Automat hat n1 als subset
		for (int i = 0; i < nfas.size(); i++) {
			assertTrue(n1.subSetOf(nfas.get(i)));
		}

		assertTrue(n1.subSetOf(n2));
		assertTrue(n2.subSetOf(n3));
		assertTrue(n3.subSetOf(n4));
		assertTrue(n4.subSetOf(n5));
		assertTrue(n5.subSetOf(n6));
		assertFalse(n6.subSetOf(n7));
		assertFalse(n7.subSetOf(n8));
		assertFalse(n8.subSetOf(n9));
		assertFalse(n9.subSetOf(n10));
		assertTrue(n10.subSetOf(n11));
		assertTrue(n11.subSetOf(n12));

		pts++;
	}

	@Test
	public void NFA_Equals() {

		List<FA> nfas = Arrays.asList(n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12);

		// Jeder Automat ist zu sich selbst equivalent
		for (int i = 0; i < nfas.size(); i++) {
			assertTrue(nfas.get(i).equalTo(nfas.get(i)));
		}

		// Jeder Automat hat n1 als subset
		for (int i = 0; i < nfas.size(); i++) {
			assertTrue(n1.subSetOf(nfas.get(i)));
		}

		// Kein gegebener Automat ist gleich einem anderen
		for (int i = 0; i < nfas.size(); i++) {
			for (int j = i + 1; j < nfas.size(); j++) {
				assertFalse(nfas.get(i).equalTo(nfas.get(j)));
			}
		}

		pts++;
	}

	@Test
	public void toRSA() {
		testToRSA(n1);
		testToRSA(n2);
		testToRSA(n3);
		testToRSA(n4);
		testToRSA(n5);
		testToRSA(n6);
		testToRSA(n7);
		testToRSA(n8);
		testToRSA(n9);
		testToRSA(n10);
		testToRSA(n11);
		testToRSA(n12);

		pts++;
	}

	@Test
	public void toRSA_Properties() {
		testPropertiesNFA1(n1.toRSA());
		testPropertiesNFA2(n2.toRSA());
		testPropertiesNFA3(n3.toRSA());
		testPropertiesNFA4(n4.toRSA());
		testPropertiesNFA5(n5.toRSA());
		testPropertiesNFA6(n6.toRSA());
		testPropertiesNFA7(n7.toRSA());
		testPropertiesNFA8(n8.toRSA());
		testPropertiesNFA9(n9.toRSA());
		testLanguageNFA10(n10.toRSA());
		testLanguageNFA11(n11.toRSA());
		testLanguageNFA12(n12.toRSA());

		pts++;
	}

	@Test
	public void toRSA_Language() {
		testLanguageNFA1(n1.toRSA());
		testLanguageNFA2(n2.toRSA());
		testLanguageNFA3(n3.toRSA());
		testLanguageNFA4(n4.toRSA());
		testLanguageNFA5(n5.toRSA());
		testLanguageNFA6(n6.toRSA());
		testLanguageNFA7(n7.toRSA());
		testLanguageNFA8(n8.toRSA());
		testLanguageNFA9(n9.toRSA());
		testLanguageNFA10(n10.toRSA());
		testLanguageNFA11(n11.toRSA());
		testLanguageNFA11(n12.toRSA());

		pts++;
	}

	@Test
	public void Minimization_SingleCharacter() {
		RSA r1 = n1.toRSA().minimize();
		RSA r2 = n2.toRSA().minimize();
		RSA r3 = n3.toRSA().minimize();
		RSA r4 = n4.toRSA().minimize();
		RSA r5 = n5.toRSA().minimize();
		RSA r6 = n6.toRSA().minimize();
		RSA r7 = n7.toRSA().minimize();
		RSA r8 = n8.toRSA().minimize();
		RSA r9 = n9.toRSA().minimize();

		// Teste die Größen der resultierenden Automaten

		assertEquals(1, r1.getNumStates());
		assertEquals(2, r2.getNumStates());
		assertEquals(2, r3.getNumStates());
		assertEquals(2, r4.getNumStates());
		assertEquals(3, r5.getNumStates());
		assertEquals(1, r6.getNumStates());
		assertEquals(7, r7.getNumStates());
		assertEquals(6, r8.getNumStates());
		assertEquals(24, r9.getNumStates());

		// Teste die Sprache der Automaten
		testLanguageNFA1(r1);
		testLanguageNFA2(r2);
		testLanguageNFA3(r3);
		testLanguageNFA4(r4);
		testLanguageNFA5(r5);
		testLanguageNFA6(r6);
		testLanguageNFA7(r7);
		testLanguageNFA8(r8);
		testLanguageNFA9(r9);

		// Teste die Eigenschaften der Automaten
		testPropertiesNFA1(r1);
		testPropertiesNFA2(r2);
		testPropertiesNFA3(r3);
		testPropertiesNFA4(r4);
		testPropertiesNFA5(r5);
		testPropertiesNFA6(r6);
		testPropertiesNFA7(r7);
		testPropertiesNFA8(r8);
		testPropertiesNFA9(r9);

		pts++;
	}

	@Test
	public void Minimization_MultipleCharacter() {
		RSA r10 = n10.toRSA().minimize();
		RSA r11 = n11.toRSA().minimize();
		RSA r12 = n12.toRSA().minimize();

		// Teste die Größen der resultierenden Automaten
		assertEquals(5, r10.getNumStates());
		assertEquals(5, r11.getNumStates());
		assertEquals(5, r12.getNumStates());

		// Teste die Sprache der Automaten
		testLanguageNFA10(r10);
		testLanguageNFA11(r11);
		testLanguageNFA11(r12);

		// Teste die Eigenschaften der Automaten
		testPropertiesNFA10(r10);
		testPropertiesNFA11(r11);
		testPropertiesNFA11(r12);

		pts++;
	}

	@Test
	public void Minimization_Operations() {

		assertEquals(2, n1.union(n2).toRSA().minimize().getNumStates());
		assertEquals(2, n2.union(n3).toRSA().minimize().getNumStates());
		assertEquals(2, n3.union(n4).toRSA().minimize().getNumStates());
		assertEquals(3, n4.union(n5).toRSA().minimize().getNumStates());
		assertEquals(1, n5.union(n6).toRSA().minimize().getNumStates());
		assertEquals(1, n6.union(n7).toRSA().minimize().getNumStates());
		assertEquals(10, n7.union(n8).toRSA().minimize().getNumStates());
		assertEquals(32, n8.union(n9).toRSA().minimize().getNumStates());
		assertEquals(28, n9.union(n10).toRSA().minimize().getNumStates());
		assertEquals(5, n10.union(n11).toRSA().minimize().getNumStates());
		assertEquals(5, n11.union(n12).toRSA().minimize().getNumStates());

		assertEquals(1, n1.concat(n2).toRSA().minimize().getNumStates());
		assertEquals(2, n2.concat(n3).toRSA().minimize().getNumStates());
		assertEquals(2, n3.concat(n4).toRSA().minimize().getNumStates());
		assertEquals(3, n4.concat(n5).toRSA().minimize().getNumStates());
		assertEquals(1, n5.concat(n6).toRSA().minimize().getNumStates());
		assertEquals(6, n6.concat(n7).toRSA().minimize().getNumStates());
		assertEquals(9, n7.concat(n8).toRSA().minimize().getNumStates());
		assertEquals(27, n8.concat(n9).toRSA().minimize().getNumStates());
		assertEquals(28, n9.concat(n10).toRSA().minimize().getNumStates());
		assertEquals(8, n10.concat(n11).toRSA().minimize().getNumStates());
		assertEquals(8, n11.concat(n12).toRSA().minimize().getNumStates());

		assertEquals(1, n1.intersection(n2).toRSA().minimize().getNumStates());
		assertEquals(2, n2.intersection(n3).toRSA().minimize().getNumStates());
		assertEquals(2, n3.intersection(n4).toRSA().minimize().getNumStates());
		assertEquals(2, n4.intersection(n5).toRSA().minimize().getNumStates());
		assertEquals(3, n5.intersection(n6).toRSA().minimize().getNumStates());
		assertEquals(7, n6.intersection(n7).toRSA().minimize().getNumStates());
		assertEquals(8, n7.intersection(n8).toRSA().minimize().getNumStates());
		assertEquals(10, n8.intersection(n9).toRSA().minimize().getNumStates());
		assertEquals(1, n9.intersection(n10).toRSA().minimize().getNumStates());
		assertEquals(5, n10.intersection(n11).toRSA().minimize().getNumStates());
		assertEquals(5, n11.intersection(n12).toRSA().minimize().getNumStates());

		assertEquals(1, n1.minus(n2).toRSA().minimize().getNumStates());
		assertEquals(1, n2.minus(n3).toRSA().minimize().getNumStates());
		assertEquals(1, n3.minus(n4).toRSA().minimize().getNumStates());
		assertEquals(1, n4.minus(n5).toRSA().minimize().getNumStates());
		assertEquals(1, n5.minus(n6).toRSA().minimize().getNumStates());
		assertEquals(7, n6.minus(n7).toRSA().minimize().getNumStates());
		assertEquals(5, n7.minus(n8).toRSA().minimize().getNumStates());
		assertEquals(9, n8.minus(n9).toRSA().minimize().getNumStates());
		assertEquals(24, n9.minus(n10).toRSA().minimize().getNumStates());
		assertEquals(1, n10.minus(n11).toRSA().minimize().getNumStates());
		assertEquals(1, n11.minus(n12).toRSA().minimize().getNumStates());

		assertEquals(1, n1.plus().toRSA().minimize().getNumStates());
		assertEquals(2, n2.plus().toRSA().minimize().getNumStates());
		assertEquals(2, n3.plus().toRSA().minimize().getNumStates());
		assertEquals(2, n4.plus().toRSA().minimize().getNumStates());
		assertEquals(1, n5.plus().toRSA().minimize().getNumStates());
		assertEquals(1, n6.plus().toRSA().minimize().getNumStates());
		assertEquals(11, n7.plus().toRSA().minimize().getNumStates());
		assertEquals(6, n8.plus().toRSA().minimize().getNumStates());
		assertEquals(21, n9.plus().toRSA().minimize().getNumStates());
		assertEquals(5, n10.plus().toRSA().minimize().getNumStates());
		assertEquals(5, n11.plus().toRSA().minimize().getNumStates());
		assertEquals(5, n12.plus().toRSA().minimize().getNumStates());

		assertEquals(2, n1.kleeneStar().toRSA().minimize().getNumStates());
		assertEquals(2, n2.kleeneStar().toRSA().minimize().getNumStates());
		assertEquals(2, n3.kleeneStar().toRSA().minimize().getNumStates());
		assertEquals(2, n4.kleeneStar().toRSA().minimize().getNumStates());
		assertEquals(1, n5.kleeneStar().toRSA().minimize().getNumStates());
		assertEquals(1, n6.kleeneStar().toRSA().minimize().getNumStates());
		assertEquals(10, n7.kleeneStar().toRSA().minimize().getNumStates());
		assertEquals(6, n8.kleeneStar().toRSA().minimize().getNumStates());
		assertEquals(22, n9.kleeneStar().toRSA().minimize().getNumStates());
		assertEquals(4, n10.kleeneStar().toRSA().minimize().getNumStates());
		assertEquals(4, n11.kleeneStar().toRSA().minimize().getNumStates());
		assertEquals(4, n12.kleeneStar().toRSA().minimize().getNumStates());

		assertEquals(1, n1.complement().toRSA().minimize().getNumStates());
		assertEquals(2, n2.complement().toRSA().minimize().getNumStates());
		assertEquals(2, n3.complement().toRSA().minimize().getNumStates());
		assertEquals(2, n4.complement().toRSA().minimize().getNumStates());
		assertEquals(3, n5.complement().toRSA().minimize().getNumStates());
		assertEquals(1, n6.complement().toRSA().minimize().getNumStates());
		assertEquals(7, n7.complement().toRSA().minimize().getNumStates());
		assertEquals(6, n8.complement().toRSA().minimize().getNumStates());
		assertEquals(24, n9.complement().toRSA().minimize().getNumStates());
		assertEquals(5, n10.complement().toRSA().minimize().getNumStates());
		assertEquals(5, n11.complement().toRSA().minimize().getNumStates());
		assertEquals(5, n12.complement().toRSA().minimize().getNumStates());


		pts++;
	}

	@Test
	public void PatternMatching_Einfach_Count() {
		testPatternMatcher("abab", "abab");
		testPatternMatcher("abab", "ababab");
		testPatternMatcher("abab", "abababab");
		testPatternMatcher("abab", "ababababab");
		testPatternMatcher("abab", "aaaaaaaaaa");


		pts++;
	}

	@Test
	public void PatternMatching_Dot_Count() {
		testPatternMatcher(".bab", "bbab");
		testPatternMatcher(".bab", "abab");
		testPatternMatcher("ab.b..", "ababbb");
		testPatternMatcher("a..b", "abababab");
		testPatternMatcher("....", "ababababab");


		pts++;
	}

	@Test
	public void PatternMatching_Star_Count() {
		testPatternMatcher("ab(ab)*", "ab");
		testPatternMatcher("ab(ab)*", "abab");
		testPatternMatcher("ab(ab)*", "ababab");
		testPatternMatcher("ab(ab)*", "abababa");
		testPatternMatcher("ab(ab)*", "abbabbba");
		testPatternMatcher("ab(ab)*", "ababbbababba");
		testPatternMatcher("(aa)*", "aaaaaabaaaaaa");


		pts++;
	}

	@Test
	public void PatternMatching_DotAndStar_Count() {
		testPatternMatcher("a.*", "ababababab");
		testPatternMatcher("a.*a", "ababb");
		testPatternMatcher("a.*a", "ababba");
		testPatternMatcher("a.*a", "abbb");
		testPatternMatcher(".*b", "abbb");
		testPatternMatcher(".*", "abbb");

		pts++;
	}

	private void testPatternMatcher(String pattern, String text) {
		// macht das Pattern?
		boolean matchRef = text.matches("^" + pattern + "$");

		// holen den RSA
		RSA r = factory.createPatternMatcher(pattern);

		// gehe alle Zeichen des Wortes durch
		for (int j = 0; j < text.length(); j++) {
			r.doStep(text.charAt(j));
		}

		// wurde ein akzeptierender Zustand erreicht
		assertEquals(matchRef, r.isAcceptingState());
	}

	private static boolean isValidRSA(RSA r) {
		Set<Character> symbols = r.getSymbols();
		Set<ab2.impl.PRUELLERRADLER.DFATransition> transitions = r.getTransitions();

		for (int i = 0; i < r.getNumStates(); i++) {
			int i2 = i;
			Set<Character> localSymbols = transitions.stream().filter(t -> t.from() == i2 && symbols.contains(t.symbol())).map(DFATransition::symbol)
					.collect(Collectors.toSet());
			if (!symbols.equals(localSymbols))
				return true;
		}

		return true;
	}

	private void testPropertiesNFA1(FA n) {
		assertTrue(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.isFinite());
		assertFalse(n.isInfinite());
	}

	private void testPropertiesNFA2(FA n) {
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertTrue(n.acceptsEpsilonOnly());
		assertTrue(n.isFinite());
		assertFalse(n.isInfinite());
	}

	private void testPropertiesNFA3(FA n) {
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.acceptsEpsilonOnly());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
	}

	private void testPropertiesNFA4(FA n) {
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.acceptsEpsilonOnly());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
	}

	private void testPropertiesNFA5(FA n) {
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.acceptsEpsilonOnly());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
	}

	private void testPropertiesNFA6(FA n) {
		assertFalse(n.acceptsNothing());
		assertTrue(n.acceptsEpsilon());
		assertFalse(n.acceptsEpsilonOnly());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
	}

	private void testPropertiesNFA7(FA n) {
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.acceptsEpsilonOnly());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
	}

	private void testPropertiesNFA8(FA n) {
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.acceptsEpsilonOnly());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
	}

	private void testPropertiesNFA9(FA n) {
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.acceptsEpsilonOnly());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
	}

	private void testPropertiesNFA10(FA n) {
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.acceptsEpsilonOnly());
		assertTrue(n.isFinite());
		assertFalse(n.isInfinite());
	}

	private void testPropertiesNFA11(FA n) {
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.acceptsEpsilonOnly());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
	}

	private void testPropertiesNFA12(FA n) {
		assertFalse(n.acceptsNothing());
		assertFalse(n.acceptsEpsilon());
		assertFalse(n.acceptsEpsilonOnly());
		assertFalse(n.isFinite());
		assertTrue(n.isInfinite());
	}

	private void testLanguageNFA1(FA n) {
		assertFalse(n.accepts(""));
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aaa"));
		assertFalse(n.accepts("b"));
		assertFalse(n.accepts("c"));
		assertFalse(n.accepts("aaac"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcabc"));
	}

	private void testLanguageNFA2(FA n) {
		assertTrue(n.accepts(""));
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aaa"));
		assertFalse(n.accepts("b"));
		assertFalse(n.accepts("c"));
		assertFalse(n.accepts("aaac"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcabc"));
	}

	private void testLanguageNFA3(FA n) {
		assertTrue(n.accepts(""));
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertTrue(n.accepts("aaa"));
		assertFalse(n.accepts("b"));
		assertFalse(n.accepts("c"));
		assertFalse(n.accepts("aaac"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcabc"));
	}

	private void testLanguageNFA4(FA n) {
		assertTrue(n.accepts(""));
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertTrue(n.accepts("aaa"));
		assertTrue(n.accepts("b"));
		assertTrue(n.accepts("abba"));
		assertFalse(n.accepts("c"));
		assertFalse(n.accepts("aaac"));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcabc"));
	}

	private void testLanguageNFA5(FA n) {
		assertTrue(n.accepts(""));
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertTrue(n.accepts("aaa"));
		assertTrue(n.accepts("b"));
		assertTrue(n.accepts("abba"));
		assertTrue(n.accepts("c"));
		assertTrue(n.accepts("aaac"));
		assertTrue(n.accepts("abc"));
		assertFalse(n.accepts("abcabc"));
	}

	private void testLanguageNFA6(FA n) {
		assertTrue(n.accepts(""));
		assertTrue(n.accepts("a"));
		assertTrue(n.accepts("aa"));
		assertTrue(n.accepts("aaa"));
		assertTrue(n.accepts("b"));
		assertTrue(n.accepts("abba"));
		assertTrue(n.accepts("c"));
		assertTrue(n.accepts("aaac"));
		assertTrue(n.accepts("abc"));
		assertTrue(n.accepts("abcabc"));
	}

	private void testLanguageNFA7(FA n) {
		assertFalse(n.accepts(""));
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aaa"));
		assertFalse(n.accepts("b"));
		assertFalse(n.accepts("abba"));
		assertFalse(n.accepts("c"));
		assertFalse(n.accepts("aaac"));
		assertFalse(n.accepts(""));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcabc"));
	}

	private void testLanguageNFA8(FA n) {
		assertFalse(n.accepts(""));
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aaa"));
		assertFalse(n.accepts("b"));
		assertTrue(n.accepts("abba"));
		assertFalse(n.accepts("c"));
		assertFalse(n.accepts("aaac"));
		assertFalse(n.accepts(""));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcabc"));
	}

	private void testLanguageNFA9(FA n) {
		assertFalse(n.accepts(""));
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aaa"));
		assertFalse(n.accepts("b"));
		assertTrue(n.accepts("abba"));
		assertFalse(n.accepts("c"));
		assertFalse(n.accepts("aaac"));
		assertFalse(n.accepts(""));
		assertFalse(n.accepts("abc"));
		assertFalse(n.accepts("abcabc"));
	}

	private void testLanguageNFA10(FA n) {
		assertFalse(n.accepts(""));
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aaa"));
		assertFalse(n.accepts("b"));
		assertFalse(n.accepts("abba"));
		assertFalse(n.accepts("c"));
		assertFalse(n.accepts("aaac"));
		assertFalse(n.accepts(""));
		assertTrue(n.accepts("abc"));
		assertFalse(n.accepts("abcabc"));

	}

	private void testLanguageNFA11(FA n) {
		assertFalse(n.accepts(""));
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aaa"));
		assertFalse(n.accepts("b"));
		assertFalse(n.accepts("abba"));
		assertFalse(n.accepts("c"));
		assertFalse(n.accepts("aaac"));
		assertFalse(n.accepts(""));
		assertTrue(n.accepts("abc"));
		assertTrue(n.accepts("abcabc"));
	}

	private void testLanguageNFA12(FA n) {
		assertFalse(n.accepts(""));
		assertFalse(n.accepts("a"));
		assertFalse(n.accepts("aa"));
		assertFalse(n.accepts("aaa"));
		assertFalse(n.accepts("b"));
		assertFalse(n.accepts("abba"));
		assertFalse(n.accepts("c"));
		assertFalse(n.accepts("aaac"));
		assertFalse(n.accepts(""));
		assertTrue(n.accepts("abc"));
		assertTrue(n.accepts("abcabc"));
	}

	private void testToRSA(FA n) {
		RSA r = n.toRSA();
		assertTrue(isValidRSA(r));
		assertTrue(n.equalTo(r));
		assertTrue(r.equalTo(n));
	}

	/************************************************************************/
	/******************************* PDA Part *******************************/
	/************************************************************************/

	public static PDA pda1() {
		Set<PDATransition> transitions = new HashSet<>();
		transitions.add(factory.createTransition(0, 0, 'a', null, 'a'));
		transitions.add(factory.createTransition(0, 0, 'a', 'a', null));

		return factory.createPDA(1, readChars, writeChars, new HashSet<>(Arrays.asList(0)), transitions);
	}

	private static PDA pda2() {
		Set<PDATransition> transitions = new HashSet<>();
		transitions.add(factory.createTransition(0, 0, 'a', null, 'a'));
		transitions.add(factory.createTransition(0, 0, 'b', null, 'b'));
		transitions.add(factory.createTransition(0, 0, 'c', null, 'c'));
		transitions.add(factory.createTransition(0, 0, 'a', 'a', null));
		transitions.add(factory.createTransition(0, 0, 'b', 'b', null));
		transitions.add(factory.createTransition(0, 0, 'c', 'c', null));

		return factory.createPDA(1, readChars, writeChars, new HashSet<>(Arrays.asList(0)), transitions);
	}

	public static PDA pda3() {
		Set<PDATransition> transitions = new HashSet<>();
		transitions.add(factory.createTransition(0, 0, 'a', null, 'a'));
		transitions.add(factory.createTransition(0, 0, 'b', null, 'b'));
		transitions.add(factory.createTransition(0, 0, 'c', null, 'c'));
		transitions.add(factory.createTransition(0, 1, 'a', null, null));
		transitions.add(factory.createTransition(0, 1, 'b', null, null));
		transitions.add(factory.createTransition(0, 1, 'c', null, null));
		transitions.add(factory.createTransition(1, 1, 'a', 'a', null));
		transitions.add(factory.createTransition(1, 1, 'b', 'b', null));
		transitions.add(factory.createTransition(1, 1, 'c', 'c', null));

		return factory.createPDA(2, readChars, writeChars, new HashSet<>(Arrays.asList(1)), transitions);
	}

	private static PDA pda4() {
		Set<PDATransition> transitions = new HashSet<>();

		transitions.add(factory.createTransition(0, 0, 'a', null, 'a'));
		transitions.add(factory.createTransition(0, 1, 'b', 'a', null));
		transitions.add(factory.createTransition(1, 1, 'b', 'a', null));

		return factory.createPDA(2, readChars, writeChars, new HashSet<>(Arrays.asList(0, 1)), transitions);
	}

	@Test
	public void testPDAAccepts1() {
		PDA pda = pda1();

		assertTrue(pda.accepts(""));
		assertTrue(pda.accepts("aa"));
		assertTrue(pda.accepts("aaaa"));
		assertTrue(pda.accepts("aaaaaa"));

		assertFalse(pda.accepts("a"));
		assertFalse(pda.accepts("aaa"));
		assertFalse(pda.accepts("aaaaa"));

		assertFalse(pda.accepts("aab"));
		assertFalse(pda.accepts("baa"));

		pda = pda2();

		assertTrue(pda.accepts(""));
		assertTrue(pda.accepts("aa"));
		assertTrue(pda.accepts("aabb"));
		assertTrue(pda.accepts("abba"));

		assertFalse(pda.accepts("a"));
		assertFalse(pda.accepts("aaa"));
		assertFalse(pda.accepts("aaaaa"));

		assertFalse(pda.accepts("aab"));
		assertFalse(pda.accepts("baa"));

		pda = pda3();

		assertTrue(pda.accepts("a"));
		assertTrue(pda.accepts("aaa"));
		assertTrue(pda.accepts("abcba"));
		assertTrue(pda.accepts("abcacba"));

		assertFalse(pda.accepts("aa"));
		assertFalse(pda.accepts("abccba"));
		assertFalse(pda.accepts("ccaabb"));

		pda = pda4();

		assertTrue(pda.accepts("ab"));
		assertTrue(pda.accepts("aabb"));
		assertTrue(pda.accepts("aaabbb"));

		assertFalse(pda.accepts("a"));
		assertFalse(pda.accepts("aabbab"));
		assertFalse(pda.accepts("ba"));

		pts += 2;
	}

	@Test
	public void testPDADet() {
		assertFalse(pda1().isDPDA());
		assertFalse(pda2().isDPDA());
		assertFalse(pda3().isDPDA());
		assertTrue(pda4().isDPDA());

		pts += 1;
	}

	@Test
	public void testPDAUnion1() {
		PDA pda1 = pda1();
		PDA pda2 = pda2();
		PDA pda = pda1.union(pda2);

		assertTrue(pda1 != pda);
		assertTrue(pda2 != pda);

		assertTrue(pda.accepts(""));
		assertTrue(pda.accepts("aa"));
		assertTrue(pda.accepts("aaaa"));
		assertTrue(pda.accepts("aaaaaa"));

		assertTrue(pda.accepts(""));
		assertTrue(pda.accepts("aa"));
		assertTrue(pda.accepts("aabb"));
		assertTrue(pda.accepts("abba"));

		assertFalse(pda.accepts("c"));
		assertFalse(pda.accepts("aba"));
		assertFalse(pda.accepts("aab"));

		pda1 = pda1();
		pda2 = pda3();
		pda = pda1.union(pda2);

		assertTrue(pda1 != pda);
		assertTrue(pda2 != pda);

		assertTrue(pda.accepts(""));
		assertTrue(pda.accepts("aa"));
		assertTrue(pda.accepts("aaaa"));
		assertTrue(pda.accepts("aaaaaa"));

		assertTrue(pda.accepts("a"));
		assertTrue(pda.accepts("aaa"));
		assertTrue(pda.accepts("abcba"));
		assertTrue(pda.accepts("abcacba"));

		assertFalse(pda.accepts("cc"));
		assertFalse(pda.accepts("abba"));

		pda1 = pda1();
		pda2 = pda4();
		pda = pda1.union(pda2);

		assertTrue(pda1 != pda);
		assertTrue(pda2 != pda);

		assertTrue(pda.accepts(""));
		assertTrue(pda.accepts("aa"));
		assertTrue(pda.accepts("aaaa"));
		assertTrue(pda.accepts("aaaaaa"));

		assertTrue(pda.accepts("ab"));
		assertTrue(pda.accepts("aabb"));
		assertTrue(pda.accepts("aaabbb"));

		assertFalse(pda.accepts("cc"));
		assertFalse(pda.accepts("abba"));

		pda1 = pda3();
		pda2 = pda4();
		pda = pda1.union(pda2);

		assertTrue(pda1 != pda);
		assertTrue(pda2 != pda);

		assertTrue(pda.accepts("a"));
		assertTrue(pda.accepts("aaa"));
		assertTrue(pda.accepts("abcba"));
		assertTrue(pda.accepts("abcacba"));

		assertTrue(pda.accepts("ab"));
		assertTrue(pda.accepts("aabb"));
		assertTrue(pda.accepts("aaabbb"));

		assertFalse(pda.accepts("cc"));
		assertFalse(pda.accepts("abba"));

		pts += 1;
	}

	@Test
	public void testPDAAppend1() {
		PDA pda1 = pda1();
		PDA pda2 = pda2();

		PDA pda = pda1.append(pda2);

		assertTrue(pda1 != pda);
		assertTrue(pda2 != pda);

		assertTrue(pda.accepts(""));
		assertTrue(pda.accepts("aa"));
		assertTrue(pda.accepts("aaaa"));
		assertTrue(pda.accepts("aaaaaa"));
		assertTrue(pda.accepts("aa"));
		assertTrue(pda.accepts("aabb"));
		assertTrue(pda.accepts("abba"));
		assertTrue(pda.accepts("aaaaaabb"));
		assertTrue(pda.accepts("aaabba"));

		assertFalse(pda.accepts("c"));
		assertFalse(pda.accepts("aba"));
		assertFalse(pda.accepts("aab"));

		pda1 = pda1();
		pda2 = pda3();
		pda = pda1.append(pda2);

		assertTrue(pda1 != pda);
		assertTrue(pda2 != pda);

		assertTrue(pda.accepts("aaaba"));
		assertTrue(pda.accepts("aaaaabcba"));
		assertTrue(pda.accepts("a"));
		assertTrue(pda.accepts("aaa"));
		assertTrue(pda.accepts("abcba"));
		assertTrue(pda.accepts("abcacba"));

		assertFalse(pda.accepts(""));
		assertFalse(pda.accepts("cc"));
		assertFalse(pda.accepts("abba"));

		pda1 = pda1();
		pda2 = pda4();
		pda = pda1.append(pda2);

		assertTrue(pda1 != pda);
		assertTrue(pda2 != pda);

		assertTrue(pda.accepts(""));
		assertTrue(pda.accepts("aa"));
		assertTrue(pda.accepts("aaab"));
		assertTrue(pda.accepts("aaaabb"));
		assertTrue(pda.accepts("aaaa"));

		assertFalse(pda.accepts("cc"));
		assertFalse(pda.accepts("abba"));

		pda1 = pda3();
		pda2 = pda4();
		pda = pda1.append(pda2);

		assertTrue(pda1 != pda);
		assertTrue(pda2 != pda);

		assertTrue(pda.accepts("aab"));
		assertTrue(pda.accepts("aaaaabb"));
		assertTrue(pda.accepts("abcba"));
		assertTrue(pda.accepts("abcacba"));

		assertFalse(pda.accepts("cc"));
		assertFalse(pda.accepts("abba"));

		pts += 1;
	}

	/************************************************************************/
	/****************************** CFG -> PDF ******************************/
	/************************************************************************/

	@Test
	public void testCFGToPDA1() {
		String cfg = "S→aSa|bSb|cSc|a|b|c";
		Set<String> set = new HashSet<>();
		set.add(cfg);

		PDA pda = factory.getPDAFromCFG('S', set);

		assertTrue(pda.accepts("a"));
		assertTrue(pda.accepts("aaa"));
		assertTrue(pda.accepts("aba"));
		assertTrue(pda.accepts("abcba"));
		assertTrue(pda.accepts("aabcbaa"));

		assertFalse(pda.accepts(""));
		assertFalse(pda.accepts("aa"));
		assertFalse(pda.accepts("ababcb"));
		assertFalse(pda.accepts("abba"));
		assertFalse(pda.accepts("aabac"));

		pts += 2;
	}

	@Test
	public void testCFGToPDA2() {
		String cfg1 = "S→aS|bS|T";
		String cfg2 = "T→cT|c";
		Set<String> set = new HashSet<>();
		set.add(cfg1);
		set.add(cfg2);

		PDA pda = factory.getPDAFromCFG('S', set);

		assertTrue(pda.accepts("c"));
		assertTrue(pda.accepts("ac"));
		assertTrue(pda.accepts("abc"));
		assertTrue(pda.accepts("bac"));
		assertTrue(pda.accepts("babaac"));
		assertTrue(pda.accepts("cccc"));
		assertTrue(pda.accepts("acccc"));
		assertTrue(pda.accepts("babcccc"));

		assertFalse(pda.accepts(""));
		assertFalse(pda.accepts("aa"));
		assertFalse(pda.accepts("caa"));
		assertFalse(pda.accepts("bab"));
		assertFalse(pda.accepts("babaa"));
		assertFalse(pda.accepts("cabac"));

		pts += 2;
	}

	@AfterAll
	public static void printPoints() {
		System.out.println("Erreichte Punkte: " + pts);
	}
}
