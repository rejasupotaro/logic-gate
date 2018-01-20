# Logic AND Gate

This is a revolutional model which predicts the output of logic AND gate.

| | AND Gate |
| -- | -- |
| input name | x |
| input shape | (1, 2) |
| output name | y_pred |
| output shape | (1, 1) |
| threshold | 0.5 |

## 1. Train Model

```
$ python train.py
```

It generates `logic_and_gate.pb` and `logic_and_gate.pb.txt` under models directory.

## 2. See Graph on TensorBoard

```
$ bazel build tensorflow/python/tools:import_pb_to_tensorboard
$ bazel-bin/tensorflow/python/tools/import_pb_to_tensorboard \
    --model_dir=../logic-gate/logic-gate-python/models/logic_and_gate.pb \
    --log_dir=../logic-gate/logic-gate-python/logs/raw/
$ tensorboard --logdir=../logic-gate/logic-gate-python/logs/raw/
```

Then, open `http://localhost:6006`

<img src="https://github.com/rejasupotaro/logic-gate/blob/master/images/logic_gate_graph_1.png?raw=true" width="360">

### 3. See Summary

```
$ bazel build tensorflow/tools/graph_transforms:summarize_graph
$ bazel-bin/tensorflow/tools/graph_transforms/summarize_graph \
    --in_graph=../logic-gate/logic-gate-python/models/logic_and_gate.pb
Found 1 possible inputs: (name=x, type=float(1), shape=[?,2])
No variables spotted.
Found 1 possible outputs: (name=y_pred, op=Sigmoid)
Found 3 (3) const parameters, 0 (0) variable parameters, and 0 control_edges
Op types used: 2 Const, 2 Identity, 1 Add, 1 MatMul, 1 Placeholder, 1 Sigmoid
```

### 4. Benchmark

```
$ bazel build tensorflow/tools/benchmark/benchmark_model
$ bazel-bin/tensorflow/tools/benchmark/benchmark_model \
--graph=../logic-gate/logic-gate-python/models/logic_and_gate.pb \
--input_layer="x" \
--input_layer_shape="1,2" \
--input_layer_type="float" \
--output_layer="y_pred" \
--show_run_order=false \
--show_time=true \
--show_memory=true \
--show_summary=true \
--show_flops=true
```

<details>

```
2017-12-27 20:42:59.748938: I tensorflow/tools/benchmark/benchmark_model.cc:443] Graph: [../logic-gate/logic-gate-python/models/logic_and_gate.pb]
2017-12-27 20:42:59.749396: I tensorflow/tools/benchmark/benchmark_model.cc:444] Input layers: [x]
2017-12-27 20:42:59.749400: I tensorflow/tools/benchmark/benchmark_model.cc:445] Input shapes: [1,2]
2017-12-27 20:42:59.749403: I tensorflow/tools/benchmark/benchmark_model.cc:446] Input types: [float]
2017-12-27 20:42:59.749406: I tensorflow/tools/benchmark/benchmark_model.cc:447] Output layers: [y_pred]
2017-12-27 20:42:59.749411: I tensorflow/tools/benchmark/benchmark_model.cc:448] Num runs: [1000]
2017-12-27 20:42:59.749414: I tensorflow/tools/benchmark/benchmark_model.cc:449] Inter-inference delay (seconds): [-1.0]
2017-12-27 20:42:59.749418: I tensorflow/tools/benchmark/benchmark_model.cc:450] Inter-benchmark delay (seconds): [-1.0]
2017-12-27 20:42:59.749421: I tensorflow/tools/benchmark/benchmark_model.cc:452] Num threads: [-1]
2017-12-27 20:42:59.749423: I tensorflow/tools/benchmark/benchmark_model.cc:453] Benchmark name: []
2017-12-27 20:42:59.749508: I tensorflow/tools/benchmark/benchmark_model.cc:454] Output prefix: []
2017-12-27 20:42:59.749520: I tensorflow/tools/benchmark/benchmark_model.cc:455] Show sizes: [0]
2017-12-27 20:42:59.749524: I tensorflow/tools/benchmark/benchmark_model.cc:456] Warmup runs: [1]
2017-12-27 20:42:59.749527: I tensorflow/tools/benchmark/benchmark_model.cc:54] Loading TensorFlow.
2017-12-27 20:42:59.749543: I tensorflow/tools/benchmark/benchmark_model.cc:61] Got config, 0 devices
2017-12-27 20:42:59.749970: I tensorflow/core/platform/cpu_feature_guard.cc:137] Your CPU supports instructions that this TensorFlow binary was not compiled to use: SSE4.2 AVX AVX2 FMA
2017-12-27 20:42:59.769199: I tensorflow/tools/benchmark/benchmark_model.cc:468] Initialized session in 0.019661s
2017-12-27 20:42:59.769253: I tensorflow/tools/benchmark/benchmark_model.cc:308] Running benchmark for max 1 iterations, max -1 seconds without detailed stat logging, with -1s sleep between inferences
2017-12-27 20:42:59.776819: I tensorflow/tools/benchmark/benchmark_model.cc:341] count=1 curr=7122

2017-12-27 20:42:59.776849: I tensorflow/tools/benchmark/benchmark_model.cc:308] Running benchmark for max 1000 iterations, max 10 seconds without detailed stat logging, with -1s sleep between inferences
2017-12-27 20:42:59.812422: I tensorflow/tools/benchmark/benchmark_model.cc:341] count=1000 first=70 curr=19 min=17 max=4614 avg=33.492 std=145

2017-12-27 20:42:59.812443: I tensorflow/tools/benchmark/benchmark_model.cc:308] Running benchmark for max 1000 iterations, max 10 seconds with detailed stat logging, with -1s sleep between inferences
2017-12-27 20:42:59.892728: I tensorflow/tools/benchmark/benchmark_model.cc:341] count=1000 first=452 curr=49 min=44 max=4644 avg=62.437 std=145

2017-12-27 20:42:59.892755: I tensorflow/tools/benchmark/benchmark_model.cc:561] Average inference timings in us: Warmup: 7122, no stats: 33, with stats: 62
2017-12-27 20:42:59.892884: I tensorflow/core/util/stat_summarizer.cc:358] Number of nodes executed: 8
2017-12-27 20:42:59.892937: I tensorflow/core/util/stat_summarizer.cc:468] ============================== Top by Computation Time ==============================
2017-12-27 20:42:59.892945: I tensorflow/core/util/stat_summarizer.cc:468] 	             [node type]	  [start]	  [first]	 [avg ms]	     [%]	  [cdf%]	  [mem KB]	[times called]	[Name]
2017-12-27 20:42:59.892950: I tensorflow/core/util/stat_summarizer.cc:468] 	                  MatMul	    0.019	    0.007	    0.004	 20.892%	 20.892%	     0.004	        1	MatMul
2017-12-27 20:42:59.892955: I tensorflow/core/util/stat_summarizer.cc:468] 	                   Const	    0.010	    0.015	    0.003	 15.396%	 36.288%	     0.000	        1	weight
2017-12-27 20:42:59.892959: I tensorflow/core/util/stat_summarizer.cc:468] 	                   Const	    0.016	    0.003	    0.003	 13.408%	 49.696%	     0.000	        1	bias
2017-12-27 20:42:59.892963: I tensorflow/core/util/stat_summarizer.cc:468] 	                     Add	    0.025	    0.004	    0.003	 13.329%	 63.025%	     0.000	        1	add
2017-12-27 20:42:59.892967: I tensorflow/core/util/stat_summarizer.cc:468] 	                    _Arg	    0.007	    0.011	    0.002	 10.527%	 73.552%	     0.000	        1	_arg_x_0_0
2017-12-27 20:42:59.892972: I tensorflow/core/util/stat_summarizer.cc:468] 	                 Sigmoid	    0.028	    0.002	    0.002	  9.815%	 83.368%	     0.000	        1	y_pred
2017-12-27 20:42:59.893006: I tensorflow/core/util/stat_summarizer.cc:468] 	                 _Retval	    0.031	    0.002	    0.002	  8.402%	 91.770%	     0.000	        1	_retval_y_pred_0_0
2017-12-27 20:42:59.893015: I tensorflow/core/util/stat_summarizer.cc:468] 	                    NoOp	    0.000	    0.331	    0.002	  8.230%	100.000%	     0.000	        1	_SOURCE
2017-12-27 20:42:59.893019: I tensorflow/core/util/stat_summarizer.cc:468]
2017-12-27 20:42:59.893022: I tensorflow/core/util/stat_summarizer.cc:468] ============================== Top by Memory Use ==============================
2017-12-27 20:42:59.893026: I tensorflow/core/util/stat_summarizer.cc:468] 	             [node type]	  [start]	  [first]	 [avg ms]	     [%]	  [cdf%]	  [mem KB]	[times called]	[Name]
2017-12-27 20:42:59.893031: I tensorflow/core/util/stat_summarizer.cc:468] 	                  MatMul	    0.019	    0.007	    0.004	 20.892%	 20.892%	     0.004	        1	MatMul
2017-12-27 20:42:59.893035: I tensorflow/core/util/stat_summarizer.cc:468] 	                    _Arg	    0.007	    0.011	    0.002	 10.527%	 31.419%	     0.000	        1	_arg_x_0_0
2017-12-27 20:42:59.893039: I tensorflow/core/util/stat_summarizer.cc:468] 	                    NoOp	    0.000	    0.331	    0.002	  8.230%	 39.650%	     0.000	        1	_SOURCE
2017-12-27 20:42:59.893043: I tensorflow/core/util/stat_summarizer.cc:468] 	                     Add	    0.025	    0.004	    0.003	 13.329%	 52.979%	     0.000	        1	add
2017-12-27 20:42:59.893047: I tensorflow/core/util/stat_summarizer.cc:468] 	                   Const	    0.016	    0.003	    0.003	 13.408%	 66.387%	     0.000	        1	bias
2017-12-27 20:42:59.893051: I tensorflow/core/util/stat_summarizer.cc:468] 	                   Const	    0.010	    0.015	    0.003	 15.396%	 81.782%	     0.000	        1	weight
2017-12-27 20:42:59.893070: I tensorflow/core/util/stat_summarizer.cc:468] 	                 _Retval	    0.031	    0.002	    0.002	  8.402%	 90.185%	     0.000	        1	_retval_y_pred_0_0
2017-12-27 20:42:59.893075: I tensorflow/core/util/stat_summarizer.cc:468] 	                 Sigmoid	    0.028	    0.002	    0.002	  9.815%	100.000%	     0.000	        1	y_pred
2017-12-27 20:42:59.893078: I tensorflow/core/util/stat_summarizer.cc:468]
2017-12-27 20:42:59.893081: I tensorflow/core/util/stat_summarizer.cc:468] ============================== Summary by node type ==============================
2017-12-27 20:42:59.893085: I tensorflow/core/util/stat_summarizer.cc:468] 	             [Node type]	  [count]	  [avg ms]	    [avg %]	    [cdf %]	  [mem KB]	[times called]
2017-12-27 20:42:59.893089: I tensorflow/core/util/stat_summarizer.cc:468] 	                   Const	        2	     0.005	    29.412%	    29.412%	     0.000	        2
2017-12-27 20:42:59.893092: I tensorflow/core/util/stat_summarizer.cc:468] 	                  MatMul	        1	     0.004	    23.529%	    52.941%	     0.004	        1
2017-12-27 20:42:59.893106: I tensorflow/core/util/stat_summarizer.cc:468] 	                    _Arg	        1	     0.002	    11.765%	    64.706%	     0.000	        1
2017-12-27 20:42:59.893116: I tensorflow/core/util/stat_summarizer.cc:468] 	                 Sigmoid	        1	     0.002	    11.765%	    76.471%	     0.000	        1
2017-12-27 20:42:59.893120: I tensorflow/core/util/stat_summarizer.cc:468] 	                     Add	        1	     0.002	    11.765%	    88.235%	     0.000	        1
2017-12-27 20:42:59.893124: I tensorflow/core/util/stat_summarizer.cc:468] 	                 _Retval	        1	     0.001	     5.882%	    94.118%	     0.000	        1
2017-12-27 20:42:59.893128: I tensorflow/core/util/stat_summarizer.cc:468] 	                    NoOp	        1	     0.001	     5.882%	   100.000%	     0.000	        1
2017-12-27 20:42:59.893131: I tensorflow/core/util/stat_summarizer.cc:468]
2017-12-27 20:42:59.893151: I tensorflow/core/util/stat_summarizer.cc:468] Timings (microseconds): count=1000 first=375 curr=19 min=14 max=375 avg=20.376 std=12
2017-12-27 20:42:59.893154: I tensorflow/core/util/stat_summarizer.cc:468] Memory (bytes): count=1000 curr=4(all same)
2017-12-27 20:42:59.893157: I tensorflow/core/util/stat_summarizer.cc:468] 8 nodes observed
2017-12-27 20:42:59.893160: I tensorflow/core/util/stat_summarizer.cc:468]
2017-12-27 20:42:59.894070: I tensorflow/tools/benchmark/benchmark_model.cc:596] FLOPs estimate: 4
2017-12-27 20:42:59.894084: I tensorflow/tools/benchmark/benchmark_model.cc:598] FLOPs/second: 119.43k
```
</details>

## 5. Optimize Graph

### Use `tensorflow/tools`

```
$ bazel build tensorflow/tools/graph_transforms:transform_graph
$ bazel-bin/tensorflow/tools/graph_transforms/transform_graph \
    --in_graph=../logic-gate/logic-gate-python/models/logic_and_gate.pb \
    --out_graph=../logic-gate/logic-gate-python/models/optimized_logic_and_gate.pb \
    --inputs='x' \
    --outputs='y_pred' \
    --transforms='strip_unused_nodes(type=float, shape="1,2")
                  remove_nodes(op=Identity, op=CheckNumerics)
                  fold_constants(ignore_errors=true)
                  fold_batch_norms
                  fold_old_batch_norms
                  quantize_weights
                  sort_by_execution_order'
```

### Use `tensorflow/python/tools`

```
$ bazel build tensorflow/python/tools:optimize_for_inference
$ bazel-bin/tensorflow/python/tools/optimize_for_inference \
    --input=../logic-gate/logic-gate-python/models/and.pb \
    --output=../logic-gate/logic-gate-python/models/optimized_and.pb \
    --frozen_graph=True \
    --input_names=x \
    --output_names=y_pred
```

```
$ bazel build tensorflow/tools/quantization:quantize_graph
$ bazel-bin/tensorflow/tools/quantization/quantize_graph \
    --input=../logic-gate/logic-gate-python/models/logic_and_gate.pb \
    --output_node_names="y_pred" \
    --print_nodes \
    --output=../logic-gate/logic-gate-python/models/optimized_logic_and_gate.pb \
    --mode=eightbit \
    --logtostderr
```

## 6. See Optimized Graph on TensorBoard

```
$ bazel build tensorflow/python/tools:import_pb_to_tensorboard
$ bazel-bin/tensorflow/python/tools/import_pb_to_tensorboard \
    --model_dir=../logic-gate/logic-gate-python/models/optimized_logic_and_gate.pb \
    --log_dir=../logic-gate/logic-gate-python/logs/optimized/
$ tensorboard --logdir=../logic-gate/logic-gate-python/logs/optimized/
```

Then, open `http://localhost:6006`

<img src="https://github.com/rejasupotaro/logic-gate/blob/master/images/logic_gate_graph_2.png?raw=true" width="360">

## 7. Optimize Graph for TensorFlow Lite

```
$ bazel build tensorflow/contrib/lite/toco:toco
$ bazel-bin/tensorflow/contrib/lite/toco/toco \
    --input_file=../logic-gate/logic-gate-python/models/optimized_logic_and_gate.pb \
    --input_format=TENSORFLOW_GRAPHDEF \
    --output_format=TFLITE \
    --output_file=../logic-gate/logic-gate-python/models/optimized_logic_and_gate.tflite \
    --inference_type=FLOAT \
    --input_type_type=FLOAT \
    --input_arrays=x \
    --output_arrays=y_pred \
    --input_shapes=1,2
```
