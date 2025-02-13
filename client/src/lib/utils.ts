import { clsx, type ClassValue } from 'clsx';
import { twMerge } from 'tailwind-merge';

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

export function getToken() {
  return 'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIyNmM4M2MyZC01MTRlLTQ3ODktYWYxOC1iOGE0OTA1N2IyZDEiLCJyb2xlIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNzM5NDgwMDIxLCJleHAiOjE3Mzk0ODM2MjF9.VBKfIEds5OIO94xpoBwT_fbghf_3ZC_3CunfcJKVlAsjzwvjwK_5NV4Zou6gQAmi';
}

export function randomInt(min: number, max: number) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}