package ch.hearc.aoc3;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
/*
public class Aoc3 {
    public static void main(String ... args) throws Exception {
        var entree = Files.readString(Path.of("src/aoc3/main/resources/input.txt"));
        var somme = entree.lines().mapToInt(l -> {
            var partie1 = l.substring(0, l.length()/2);
            var partie2 = l.substring(l.length()/2);
            return prioriteCommune(List.of(partie1, partie2));
        }).sum();
        // Devrait être égal à 7872
        System.out.printf("partie 1 : %d\n", somme);

        var compteur = IntStream.range(0, entree.length()).iterator();
        var somme2 = entree.lines()
                .collect(Collectors.groupingBy(c -> compteur.nextInt()/3)).values().stream()
                .mapToInt(Aoc3::prioriteCommune).sum();
        // Devrait être égal à 2497
        System.out.printf("partie 2 : %d\n", somme2);
    }

    static int prioriteCommune(List<String> chaines) {
        // caractère commun dans une liste de chaînes
        var c = chaines.stream()
                .reduce(Aoc3::caracteresCommuns)
                .orElse("")
                .charAt(0);
        return c < 'a' ? c - 'A' + 27 : c - 'a' + 1; // convertir en priorité
    }

    static String caracteresCommuns(String a, String b) {
        return a.chars().filter(i1 -> b.chars().anyMatch(i2 -> i1 == i2)).distinct()
                .mapToObj(Character::toString).collect(Collectors.joining());
    }
}

 */

import java.util.*;

import java.util.*;
import java.nio.file.*;
import java.io.IOException;

public class Aoc3 {
    public static void main(String[] args) {
        List<String> rucksacks = new ArrayList<>();
        try {
            rucksacks = Files.readAllLines(Paths.get("src/aoc3/main/resources/input.txt"));
        } catch (IOException e) {
            System.err.println("Error reading input.txt: " + e.getMessage());
            System.exit(1);
        }

        int sumOfPriorities = 0;
        for (int i = 0; i < rucksacks.size(); i += 3) {
            sumOfPriorities += findPriorityOfCommonBadgeItem(rucksacks.subList(i, i + 3));
        }

        System.out.println("Sum of priorities: " + sumOfPriorities);
    }

    static int findPriorityOfCommonBadgeItem(List<String> group) {
        Set<Character> commonItems = new HashSet<>();
        for (char c : group.get(0).toCharArray()) {
            commonItems.add(c);
        }

        for (int i = 1; i < group.size(); i++) {
            Set<Character> currentRucksackItems = new HashSet<>();
            for (char c : group.get(i).toCharArray()) {
                currentRucksackItems.add(c);
            }
            commonItems.retainAll(currentRucksackItems);
        }

        char commonBadgeItem = commonItems.iterator().next();
        return getItemPriority(commonBadgeItem);
    }

    static int findPriorityOfSharedItemType(String rucksack) {
        int n = rucksack.length() / 2;
        Set<Character> firstCompartment = new HashSet<>();
        Set<Character> secondCompartment = new HashSet<>();

        for (int i = 0; i < n; i++) {
            firstCompartment.add(rucksack.charAt(i));
        }

        for (int i = n; i < rucksack.length(); i++) {
            secondCompartment.add(rucksack.charAt(i));
        }

        firstCompartment.retainAll(secondCompartment);

        char sharedItem = firstCompartment.iterator().next();
        return getItemPriority(sharedItem);
    }

    static int getItemPriority(char item) {
        if ('a' <= item && item <= 'z') {
            return item - 'a' + 1;
        } else if ('A' <= item && item <= 'Z') {
            return item - 'A' + 27;
        } else {
            throw new IllegalArgumentException("Invalid item type");
        }
    }
}

