### Optimize for inference

```
$ bazel build tensorflow/python/tools:optimize_for_inference
$ bazel-bin/tensorflow/python/tools/optimize_for_inference \
--input=path/to/models/and.pb \
--output=path/to/models/optimized_and.pb \
--frozen_graph=True \
--input_names=x \
--output_names=y
```

### Import pb to TensorBoard


```
$ bazel build tensorflow/python/tools:import_pb_to_tensorboard
$ bazel-bin/tensorflow/python/tools/import_pb_to_tensorboard --model_dir=path/to/models/and.pb --log_dir=path/to/logs/
```

### Quantize

```
$ bazel build tensorflow/tools/graph_transforms:transform_graph
$ bazel-bin/tensorflow/tools/graph_transforms/transform_graph \
--in_graph=../logic-gate/logic-gate-python/models/optimized_and.pb \
--out_graph=../logic-gate/logic-gate-python/models/quantized_and.pb \
--transforms='quantize_weights quantize_nodes'
```
