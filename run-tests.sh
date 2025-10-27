#!/bin/bash

# TDD Test Execution Scripts
# This file contains useful bash scripts for running tests

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}=== KMP TDD Test Execution Guide ===${NC}\n"

# Function to print commands
print_command() {
    echo -e "${YELLOW}$1${NC}"
}

# Function to print description
print_desc() {
    echo -e "${BLUE}→ $1${NC}"
}

# ============================================
# Basic Test Execution
# ============================================
print_desc "1. RUN ALL TESTS"
print_command "./gradlew test"
echo ""

# ============================================
# Module-Specific Tests
# ============================================
print_desc "2. RUN CORE MODULE TESTS"
print_command "./gradlew coreMobile:commonTest"
echo ""

print_desc "3. RUN SHARED MODULE TESTS"
print_command "./gradlew shared:commonTest"
echo ""

# ============================================
# Class-Specific Tests
# ============================================
print_desc "4. RUN SPECIFIC TEST CLASS"
print_command "./gradlew test --tests \"*CoreViewModelTest*\""
echo ""

# ============================================
# Test with Output
# ============================================
print_desc "5. RUN TESTS WITH DETAILED OUTPUT"
print_command "./gradlew test --info"
echo ""

# ============================================
# Test Without Cache
# ============================================
print_desc "6. RUN TESTS (FRESH BUILD)"
print_command "./gradlew test --no-build-cache"
echo ""

# ============================================
# Parallel Test Execution
# ============================================
print_desc "7. RUN TESTS IN PARALLEL"
print_command "./gradlew test --parallel"
echo ""

# ============================================
# Test with Coverage (if configured)
# ============================================
print_desc "8. RUN TESTS WITH COVERAGE"
print_command "./gradlew test jacocoTestReport"
echo ""

# ============================================
# Watch Mode (continuous testing)
# ============================================
print_desc "9. RUN TESTS IN WATCH MODE (Mac/Linux)"
print_command "watch -n 5 './gradlew test'"
echo ""

# ============================================
# Test Statistics
# ============================================
print_desc "10. GET TEST STATISTICS"
print_command "./gradlew test --tests \"*\" --info 2>&1 | grep -E 'Test.*completed|tests run'"
echo ""

# ============================================
# Quick Tests (Fast Suite)
# ============================================
print_desc "11. QUICK TEST SUITE (Core Models)"
print_command "./gradlew test --tests \"*Test\" --exclude-task \"*IntegrationTest*\""
echo ""

# ============================================
# Test Filtering Examples
# ============================================
echo -e "${BLUE}=== Test Filtering Examples ===${NC}\n"

print_desc "Run all ViewModels tests:"
print_command "./gradlew test --tests \"*ViewModelTest\""
echo ""

print_desc "Run all DeepLink tests:"
print_command "./gradlew test --tests \"*DeepLink*\""
echo ""

print_desc "Run all Error tests:"
print_command "./gradlew test --tests \"*Error*\""
echo ""

print_desc "Run specific test method:"
print_command "./gradlew test --tests \"*CoreViewModelTest.test*\""
echo ""

# ============================================
# Suggested Test Execution Order
# ============================================
echo -e "${GREEN}=== Recommended Test Execution Order ===${NC}\n"

echo "1. Quick smoke test (5-10 seconds):"
print_command "./gradlew coreMobile:commonTest"
echo ""

echo "2. Full test suite (30-60 seconds):"
print_command "./gradlew test"
echo ""

echo "3. Continuous testing during development:"
print_command "watch -n 2 './gradlew test --parallel'"
echo ""

# ============================================
# Debugging Failed Tests
# ============================================
echo -e "${YELLOW}=== Debugging Failed Tests ===${NC}\n"

print_desc "Run with stack trace:"
print_command "./gradlew test --stacktrace"
echo ""

print_desc "Run with debug logging:"
print_command "./gradlew test --debug"
echo ""

print_desc "Stop on first failure:"
print_command "./gradlew test --fail-fast"
echo ""

# ============================================
# Test Report Location
# ============================================
echo -e "${BLUE}=== Test Reports ===${NC}\n"

echo "HTML Report Location:"
echo -e "${GREEN}build/reports/tests/test/index.html${NC}\n"

echo "JUnit XML Report:"
echo -e "${GREEN}build/test-results/test/TEST-*.xml${NC}\n"

# ============================================
# Environment Variables for Testing
# ============================================
echo -e "${BLUE}=== Environment Variables ===${NC}\n"

print_desc "Set JVM memory for tests:"
print_command "export JAVA_OPTS=\"-Xmx2g\" && ./gradlew test"
echo ""

print_desc "Disable parallel testing:"
print_command "./gradlew test --no-parallel"
echo ""

# ============================================
# Test Documentation
# ============================================
echo -e "${BLUE}=== Documentation Files ===${NC}\n"

echo "Quick Start Guide:"
echo -e "${GREEN}QUICK_START_TESTING.md${NC}"
echo ""

echo "Comprehensive Guide:"
echo -e "${GREEN}TESTING_GUIDE.md${NC}"
echo ""

echo "Implementation Summary:"
echo -e "${GREEN}TEST_IMPLEMENTATION_SUMMARY.md${NC}"
echo ""

# ============================================
# Development Workflow
# ============================================
echo -e "${GREEN}=== Development Workflow ===${NC}\n"

echo "1. Write test first (TDD)"
echo "2. Run test to see it fail (RED)"
echo "3. Write minimal code to pass (GREEN)"
echo "4. Refactor code and tests (BLUE)"
echo "5. Repeat\n"

print_command "cd /Users/munirahmad/Pojects/My/CMPApp && ./gradlew test"
echo ""

# ============================================
# End Message
# ============================================
echo -e "${GREEN}✅ Ready to test!${NC}"
echo -e "${YELLOW}For more information, read QUICK_START_TESTING.md or TESTING_GUIDE.md${NC}"

