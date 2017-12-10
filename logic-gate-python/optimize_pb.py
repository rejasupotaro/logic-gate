from tensorflow.core.framework import graph_pb2
from tensorflow.python.framework import dtypes
from tensorflow.python.platform import gfile
from tensorflow.python.tools import optimize_for_inference_lib


def optimize_pb(model_name):
    with gfile.Open('models/{}'.format(model_name), "rb") as file:
        input_graph_def = graph_pb2.GraphDef()
        input_graph_def.ParseFromString(file.read())

        output_graph_def = optimize_for_inference_lib.optimize_for_inference(
            input_graph_def,
            input_node_names=['x'],
            output_node_names=['y_pred'],
            placeholder_type_enum=dtypes.float32.as_datatype_enum
        )

        file = gfile.FastGFile('models/optimized_{}'.format(model_name), "w")
        file.write(output_graph_def.SerializeToString())


if __name__ == '__main__':
    optimize_pb('and.pb')
