#!/usr/bin/env python3

from random import Random


def rand_shift1(nums, rand):
    shifted_nums = []
    for num in nums:
        shifted = num + rand.randrange(-10, 11)
        if shifted > 0:
            shifted_nums.append(shifted)
    return shifted_nums


def rand_shift2(nums, rand):
    return list(filter(lambda shifted: shifted > 0,
                       map(lambda num: num + rand.randrange(-10, 11), nums)))


def rand_shift3(nums, rand):
    return [shifted for shifted in (num + rand.randrange(-10, 11) for num in nums)
            if shifted > 0]


print(rand_shift1([3, 17, 5, 23, 41, 12, 10, 5], Random(1)))
print(rand_shift2([3, 17, 5, 23, 41, 12, 10, 5], Random(1)))
print(rand_shift3([3, 17, 5, 23, 41, 12, 10, 5], Random(1)))
